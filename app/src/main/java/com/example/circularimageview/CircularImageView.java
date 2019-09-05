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

public class CircularImageView extends AppCompatImageView {
    public int StrokeWidth ;
    public int StrokeColor;
    public static int ImageBGColor ;
    public static float ImageCropSize=2;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircularImageViewAttrs,

                0, 0);

        try {

            StrokeColor = a.getColor(R.styleable.CircularImageViewAttrs_strokeColor,Color.BLACK);
            ImageBGColor=a.getColor(R.styleable.CircularImageViewAttrs_imageBGColor, Color.BLACK);
            StrokeWidth=a.getInt(R.styleable.CircularImageViewAttrs_strokeWidth,0);
            //ImageCropSize=(float) (a.getInt(R.styleable.MLRoundedImageView_StrokeWidth,2)*0.10f);
           // ImageCropSize=a.getFloat(R.styleable.MLRoundedImageView_ImageCropSize,2);
        } finally {
            a.recycle();
        }
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
     //   super.onDraw(canvas);
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmapCopy=b.copy(Bitmap.Config.ARGB_8888, true);
       int  smallestSide=Math.min(getWidth(),getHeight());
        Bitmap roundBitmap = getCroppedBitmap(bitmapCopy, smallestSide,getWidth(),getHeight());

        canvas.drawBitmap(roundBitmap, ((getWidth()/2)-(roundBitmap.getWidth()/2)), (getHeight()/2)-(roundBitmap.getHeight()/2), null);


//        // stroke
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStrokeWidth(StrokeWidth);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(StrokeColor);
        //canvas.drawCircle(getWidth() / 2 -0.1f, getWidth() / 2 -0.1f, getWidth() / 2-1 , paint);
        canvas.drawCircle(getWidth()/2,getHeight()/2,(smallestSide)/2-StrokeWidth/2, paint);
        // end of stroke
    }
    public static Bitmap getCroppedBitmap(Bitmap bitmap,int ImageViewSmallestSide,int w,int h){

        Bitmap resizedBitmap=resizeImageIfNeeded(bitmap,ImageViewSmallestSide);
        //We give it the smallest side twice because we need to make it in shape of square and not go out of the border of the image view
        Bitmap output = Bitmap.createBitmap(ImageViewSmallestSide, ImageViewSmallestSide, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        int rad = ImageViewSmallestSide/2;
        Rect rect = new Rect(0,0, ImageViewSmallestSide, ImageViewSmallestSide);
        Rect rectSrc  = new Rect(resizedBitmap.getWidth()/2-rad, resizedBitmap.getHeight()/2-rad,resizedBitmap.getWidth()/2+rad, resizedBitmap.getHeight()/2+rad);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(ImageBGColor);
        canvas.drawCircle(ImageViewSmallestSide/2,ImageViewSmallestSide/2,(ImageViewSmallestSide/2),paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(resizedBitmap,rect ,rect,paint);



        return output;
    }

    private static Bitmap resizeImageIfNeeded(Bitmap bitmap, int ImageViewSmallestSide) {
        Bitmap sbmp;
        if (bitmap.getWidth()!=ImageViewSmallestSide || bitmap.getHeight()!=ImageViewSmallestSide){
        float smallest=Math.min(bitmap.getWidth(),bitmap.getHeight());
        float resizingFactor = smallest/ImageViewSmallestSide;
            sbmp= Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth() / resizingFactor), (int)(bitmap.getHeight() / resizingFactor), false);
        }
        else {
            sbmp=bitmap;
        }
        return sbmp;
    }
}
