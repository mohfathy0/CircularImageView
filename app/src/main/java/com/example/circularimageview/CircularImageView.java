package com.example.circularimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class CircularImageView extends AppCompatImageView {
    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int ImageViewWidth = getWidth(), ImageViewHight = getHeight();

       int  smallest=Math.min(getWidth(),getHeight());
        Bitmap roundBitmap = getCroppedBitmap(b, smallest);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }
    public static Bitmap getCroppedBitmap(Bitmap bitmap,int ImageViewSmallest){

        if (bitmap.getWidth()!=ImageViewSmallest || bitmap.getHeight()!=ImageViewSmallest){
            float smallest=Math.min(bitmap.getWidth(),bitmap.getHeight());
            float factor = smallest/ImageViewSmallest;
        }

        return null;
    }
}
