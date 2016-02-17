package com.yeungeek.imageloadersample.custom.v2.imageloader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;

/**
 * user lru cache
 * Created by yeungeek on 2016/2/11.
 */
public class MemoryCache implements BitmapCache {
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //1/8
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //value.getByteCount() == value.getRowBytes() * value.getHeight() ;
                return value.getByteCount() / 1024;
            }
        };
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return mMemoryCache.get(key.imageUriMd5);
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        mMemoryCache.put(key.imageUriMd5, bitmap);
    }

    @Override
    public boolean remove(BitmapRequest key) {
        return null != mMemoryCache.remove(key.imageUriMd5);
    }
}
