package com.yeungeek.imageloadersample.custom.v2.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;

/**
 * Created by yeungeek on 2016/3/1.
 */
public class DoubleCache implements BitmapCache {
    DiskCache mDiskCache;
    MemoryCache mMemoryCache = new MemoryCache();

    public DoubleCache(final Context context) {
        mDiskCache = DiskCache.getDiskCache(context);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        Bitmap bitmap = mMemoryCache.get(key);
        if (null != bitmap) {
            return bitmap;
        }

        bitmap = mDiskCache.get(key);
        if (null != bitmap) {
            mMemoryCache.put(key, bitmap);
        }

        return bitmap;
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        mDiskCache.put(key, bitmap);
        mMemoryCache.put(key, bitmap);
    }

    @Override
    public boolean remove(BitmapRequest key) {
        mDiskCache.remove(key);
        mMemoryCache.remove(key);
        return true;
    }
}
