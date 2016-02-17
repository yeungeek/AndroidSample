package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.widget.ImageView;

import com.yeungeek.imageloadersample.custom.v2.imageloader.config.DisplayConfig;

import java.lang.ref.Reference;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class BitmapRequest {
    Reference<ImageView> mImageViewRef;

    public DisplayConfig displayConfig;
    public SimpleImageLoader.ImageListener imageListener;

    public int reqWidth;
    public int reqHeight;

    public String imageUri = "";
    public String imageUriMd5 = "";

    public int serialNum = 0;
    public boolean justCacheInMem = false;
    public boolean isCancel = false;

    public ImageView getImageView() {
        return mImageViewRef.get();
    }

    /**
     * tag url
     * @return
     */
    public boolean isImageViewTagValid() {
        return mImageViewRef.get() != null ? mImageViewRef.get().getTag().equals(imageUri) : false;
    }
}
