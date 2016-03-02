package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.BitmapCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.MemoryCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.config.DisplayConfig;
import com.yeungeek.imageloadersample.custom.v2.imageloader.config.ImageLoaderConfig;
import com.yeungeek.imageloadersample.custom.v2.imageloader.policy.SerialPolicy;

/**
 * Created by yeungeek on 2016/2/11.
 */
public final class SimpleImageLoader {
    private volatile BitmapCache mCache = new MemoryCache();
    private static SimpleImageLoader mInstance;
    private RequestQueue mImageQueue;

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

    public void init(final ImageLoaderConfig config) {
        this.mConfig = config;
        this.mCache = config.bitmapCache;
        checkConfig();
        this.mImageQueue = new RequestQueue(mConfig.threadCount);
        mImageQueue.start();
    }

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }

    public void displayImage(final ImageView imageView, final String uri) {
        displayImage(imageView, uri, null, null);
    }

    public void displayImage(final ImageView imageView, final String uri, final ImageListener listener) {
        displayImage(imageView, uri, null, listener);
    }

    public void displayImage(final ImageView imageView, final String uri, final DisplayConfig config, final ImageListener listener) {
        BitmapRequest request = new BitmapRequest(imageView, uri, config, listener);
        request.displayConfig = request.displayConfig != null ? request.displayConfig : mConfig.displayConfig;
        mImageQueue.addRequest(request);
    }

    public void stop() {
        mImageQueue.stop();
    }

    private void checkConfig() {
        if (mConfig == null) {
            throw new RuntimeException(
                    "The config of SimpleImageLoader is Null, please call the init(ImageLoaderConfig config) method to initialize");
        }
        if (mConfig.loadPolicy == null) {
            mConfig.loadPolicy = new SerialPolicy();
        }
        if (mCache == null) {
            mCache = new MemoryCache();
        }
    }

    public static interface ImageListener {
        public void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }
}
