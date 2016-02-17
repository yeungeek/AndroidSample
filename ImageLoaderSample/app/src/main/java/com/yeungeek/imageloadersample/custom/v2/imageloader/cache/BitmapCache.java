package com.yeungeek.imageloadersample.custom.v2.imageloader.cache;

import android.graphics.Bitmap;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;

/**
 * Created by yeungeek on 2016/2/11.
 */
public interface BitmapCache {
    public Bitmap get(BitmapRequest key);

    public void put(BitmapRequest key, Bitmap bitmap);

    public boolean remove(BitmapRequest key);
}
