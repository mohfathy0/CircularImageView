package com.example.circularimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


public class MLRoundedImageView extends AppCompatImageView {
    // source : https://gist.github.com/melanke/7158342

    public int StrokeWidth = 2;
    public int StrokeColor;
    public static int ImageBGColor ;
    public static float ImageCropSize=2;

    public MLRoundedImageView(Context context) {
        super(context);
    }

    public MLRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MLRoundedImageView,

                0, 0);

        try {

            StrokeColor = a.getColor(R.styleable.MLRoundedImageView_stroke_color,Color.BLACK);
            ImageBGColor=a.getColor(R.styleable.MLRoundedImageView_ImageBGColor, Color.BLACK);
            StrokeWidth=a.getInt(R.styleable.MLRoundedImageView_StrokeWidth,2);
            //ImageCropSize=(float) (a.getInt(R.styleable.MLRoundedImageView_StrokeWidth,2)*0.10f);
            ImageCropSize=a.getFloat(R.styleable.MLRoundedImageView_ImageCropSize,2);
        } finally {
            a.recycle();
        }
    }

    public MLRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


    }







    @Override
    protected void onDraw(Canvas canvas) {


        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();


        Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
// stroke

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStrokeWidth(StrokeWidth);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(StrokeColor);
        canvas.drawCircle(w / 2 -0.1f,
                w / 2 -0.1f, w / 2-1 , paint);

        // end of stroke
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth() / factor), (int)(bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(ImageBGColor);
        canvas.drawCircle(radius / 2 -0.7f,
                radius / 2 -0.7f, radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

}