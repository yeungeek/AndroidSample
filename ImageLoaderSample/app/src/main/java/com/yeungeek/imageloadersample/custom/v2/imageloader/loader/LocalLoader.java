package com.yeungeek.imageloadersample.custom.v2.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapDecoder;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;

import java.io.File;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class LocalLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        final String imagePath = Uri.parse(request.imageUri).getPath();
        final File file = new File(imagePath);
        if (!file.exists()) {
            return null;
        }

        //from sd, just cache in memory
        request.justCacheInMem = true;

        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(imagePath, options);
            }
        };
        return decoder.decodeBitmap(request.getImageViewWidth(), request.getImageViewHeight());
    }
}
