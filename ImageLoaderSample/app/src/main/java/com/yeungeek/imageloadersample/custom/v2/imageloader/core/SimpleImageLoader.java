package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.BitmapCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.MemoryCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.config.ImageLoaderConfig;

/**
 * Created by yeungeek on 2016/2/11.
 */
public final class SimpleImageLoader {
    private volatile BitmapCache mCache = new MemoryCache();
    private static SimpleImageLoader mInstance;

    private ImageLoaderConfig mConfig;

    private SimpleImageLoader() {
    }

    public static SimpleImageLoader instance() {
        if (null == mInstance) {
            synchronized (SimpleImageLoader.class) {
                if (null == mInstance) {
                    mInstance = new SimpleImageLoader();
                }
            }
        }

        return mInstance;
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }

    public static interface ImageListener {
        public void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }
}
