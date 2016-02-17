package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by yeungeek on 2016/2/11.
 */
public abstract class BitmapDecoder {
    public abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);

    public Bitmap decodeBitmap(final int reqWidth, final int reqHeight) {
        return decodeBitmap(reqWidth, reqHeight, false);
    }

    protected Bitmap decodeBitmap(int reqWidth, int reqHeight, boolean reqOrigin) {
        if (reqOrigin || reqWidth <= 0 || reqHeight <= 0) {
            return decodeBitmapWithOption(null);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        decodeBitmapWithOption(options);
        //calculate
        calculateInSample(options, reqWidth, reqHeight);
        return decodeBitmapWithOption(options);
    }

    private void calculateInSample(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        options.inSampleSize = getInSampleSize(options, reqWidth, reqHeight);

        Log.d("", "## inSampleSize = " + options.inSampleSize
                + ", reqWidth = " + reqWidth + ", reqHeight= " + reqHeight);

        //rgb8888
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        options.inJustDecodeBounds = false;

        //pixels
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    private int getInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            //ratio
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            final int heightRatio = Math.round((float) height / (float) reqHeight);

            inSampleSize = Math.min(widthRatio, heightRatio);
            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            final float totalPixels = width * height;

            final float totalReqPixels = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixels) {
                inSampleSize++;
            }
        }

        return inSampleSize;
    }
}
