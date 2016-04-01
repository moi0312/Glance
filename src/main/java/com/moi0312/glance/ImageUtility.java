package com.moi0312.glance;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.widget.ImageView;

public class ImageUtility {
    private static final String TAG = ImageUtility.class.getSimpleName();
    /**
     * draw a Circle Bitmap
     * @param pixelSize
     * @param circleColor
     * @return
     */
    public static Bitmap drawCircleBitmap(int pixelSize, int circleColor){
        Bitmap circleBitmap = Bitmap.createBitmap(pixelSize, pixelSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setColor(circleColor);
        //draw a bitmap for mask shape
        canvas.drawCircle(pixelSize/2, pixelSize/2, pixelSize/2, paint);
        return circleBitmap;
    }

    /** apply a RoundRect Mask to a imageview
     *
     * @param imageView     the imageview to apply circle mask
     * @param oriBitmap     the original bitmap
     * @param pixelWidth    result width in pixel
     * @param pixelHeight   result height in pixel
     * @param roundsize     the size of round
     */
    public static void applyRoundRectMask(ImageView imageView, Bitmap oriBitmap, int pixelWidth, int pixelHeight, int roundsize) {
        Bitmap mask = Bitmap.createBitmap(pixelWidth, pixelHeight, Bitmap.Config.ARGB_8888);
        Canvas maskCanvas = new Canvas(mask);
        Paint maskPaint = new Paint();
        maskPaint.setColor(-1); // 0xFFFFFF white

        //draw a bitmap for mask shape
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maskCanvas.drawRoundRect(0, 0, pixelWidth, pixelHeight, roundsize, roundsize, maskPaint);
        }else{
            RectF rect = new RectF();
            rect.intersects(0, 0, pixelWidth, pixelHeight);
            maskCanvas.drawRoundRect(rect, roundsize, roundsize, maskPaint);

        }

        imageView.setImageBitmap( maskBitmap(oriBitmap, mask, pixelWidth, pixelHeight, true) );
        imageView.setScaleType(ImageView.ScaleType.CENTER);
    }

    /** apply a RoundRect Mask to a imageview
     *
     * @param imageView     the imageview to apply circle mask
     * @param oriBitmap     the original bitmap
     * @param pixelWidth    result width in pixel
     * @param pixelHeight   result height in pixel
     */
    public static void applyOvalMask(ImageView imageView, Bitmap oriBitmap, int pixelWidth, int pixelHeight) {
        Bitmap mask = Bitmap.createBitmap(pixelWidth, pixelHeight, Bitmap.Config.ARGB_8888);
        Canvas maskCanvas = new Canvas(mask);
        Paint maskPaint = new Paint();
        maskPaint.setColor(-1); // 0xFFFFFF white

        //draw a bitmap for mask shape
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maskCanvas.drawOval(0, 0, pixelWidth, pixelHeight, maskPaint);
        }else{
            RectF rect = new RectF();
            rect.intersects(0, 0, pixelWidth, pixelHeight);
            maskCanvas.drawOval(rect, maskPaint);
        }

        imageView.setImageBitmap( maskBitmap(oriBitmap, mask, pixelWidth, pixelHeight, true) );
        imageView.setScaleType(ImageView.ScaleType.CENTER);
    }

    /** apply a Circle Mask to a imageview
     *
     * @param imageView the imageview to apply circle mask
     * @param oriBitmap the original bitmap
     * @param pixelSize the size in pixel
     */
    public static void applyCircleMask(ImageView imageView, Bitmap oriBitmap, int pixelSize) {
        Bitmap mask = drawCircleBitmap(pixelSize, -1); // 0xFFFFFF white
        imageView.setImageBitmap( maskBitmap(oriBitmap, mask, pixelSize, pixelSize, true) );
        imageView.setScaleType(ImageView.ScaleType.CENTER);
    }

    /**apply a mask bitmap to the ori bitmap<br>
     * <b>(if keepAspectRatio is false be careful that may change the aspect ratio of the ori bitmap)</b>
     *
     * @param oriBitmap     the original bitmap
     * @param maskBitmap    the bitmap to be a mask
     * @param pixelWidth    result width in pixel
     * @param pixelHeight   result height in pixel
     * @param keepAspectRatio   keep the aspect ratio or not
     * @return
     */
    public static Bitmap maskBitmap(Bitmap oriBitmap, Bitmap maskBitmap, int pixelWidth, int pixelHeight, boolean keepAspectRatio){
        float scale = 1f;
        float leftOffset = 0f;
        float topOffset = 0f;
        int oriW =  oriBitmap.getWidth();
        int oriH =  oriBitmap.getHeight();
        //scale the ori Bitmap to fit target size
//        Log.v("edlo", "ori: "+oriW+" x "+oriH);
//        Log.v("edlo", "target: "+pixelWidth+" x "+pixelHeight);
        if(keepAspectRatio) {
            if (oriW > oriH) {
                scale = (float)pixelHeight / oriH;
            } else {
                scale = (float)pixelWidth / oriW;
            }
//            Log.v("edlo", "scale: "+scale);
            leftOffset = (oriW*scale - pixelWidth) / -2;
            topOffset = (oriH*scale - pixelHeight) / -2;
//            Log.v("edlo", "leftOffset: "+leftOffset+", topOffset: "+topOffset);
            oriBitmap = Bitmap.createScaledBitmap(oriBitmap, (int)(oriW*scale), (int)(oriH*scale), false);
        }else{
            //be careful if the pixelWidth and pixelHeight change the aspect ratio of the ori bitmap
            oriBitmap = Bitmap.createScaledBitmap(oriBitmap, pixelWidth, pixelHeight, false);
        }
        //
        Bitmap result = Bitmap.createBitmap(pixelWidth, pixelHeight, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN)); //mask
        mCanvas.drawBitmap(oriBitmap, leftOffset, topOffset, null);
        mCanvas.drawBitmap(maskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        //recycle
        oriBitmap.recycle();
        maskBitmap.recycle();

        return result;
    }

    public static boolean isJPGImageFile(String fileName) {
        String fileExtensionStr = fileName.substring(fileName.lastIndexOf(".") + 1);
//        Log.v("isJPGImageFile", "fileExtentionStr: " + fileExtensionStr);
        if (fileExtensionStr.equalsIgnoreCase("JPG") || fileExtensionStr.equalsIgnoreCase("JPEG")) {
            return true;
        }
        return false;
    }
}