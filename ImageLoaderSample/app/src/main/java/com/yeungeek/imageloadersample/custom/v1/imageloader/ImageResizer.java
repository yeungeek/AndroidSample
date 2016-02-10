package com.yeungeek.imageloadersample.custom.v1.imageloader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by yeungeek on 2016/2/16.
 */
public class ImageResizer {
    public Bitmap decodeBitmapFromRes(final Resources res, final int resId, final int reqWidth, final int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap decodeBitmapFromFile(final FileDescriptor fd, final int reqWidth, final int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFileDescriptor(fd, null, options);

        options.inSampleSize = calInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    private int calInSampleSize(BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
        if (options == null || reqHeight == 0 || reqWidth == 0) {
            return 1;
        }

        //origin
        final int width = options.outWidth;
        final int height = options.outHeight;

        int sampleSize = 1; //sampleSize
        if (width > reqWidth || height > reqHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            //largest inSampleSize
            while ((halfWidth / sampleSize >= reqWidth) && (halfHeight / sampleSize >= reqHeight)) {
                sampleSize *= 2;
            }
        }

        return sampleSize;
    }
}
