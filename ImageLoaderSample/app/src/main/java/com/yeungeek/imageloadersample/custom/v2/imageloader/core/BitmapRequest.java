package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.widget.ImageView;

import com.yeungeek.imageloadersample.custom.util.ImageViewUitls;
import com.yeungeek.imageloadersample.custom.util.Md5Utils;
import com.yeungeek.imageloadersample.custom.v2.imageloader.config.DisplayConfig;
import com.yeungeek.imageloadersample.custom.v2.imageloader.policy.LoadPolicy;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class BitmapRequest implements Comparable<BitmapRequest> {
    Reference<ImageView> mImageViewRef;

    public DisplayConfig displayConfig;
    public SimpleImageLoader.ImageListener imageListener;

//    public int reqWidth;
//    public int reqHeight;

    public String imageUri = "";
    public String imageUriMd5 = "";

    public int serialNum = 0;
    public boolean justCacheInMem = false;
    public boolean isCancel = false;
    LoadPolicy mLoadPolicy = SimpleImageLoader.instance().getConfig().loadPolicy;

    public BitmapRequest(final ImageView imageView, final String uri, final DisplayConfig config, SimpleImageLoader.ImageListener listener) {
        this.mImageViewRef = new WeakReference<ImageView>(imageView);
        this.displayConfig = config;
        this.imageListener = listener;
        this.imageUri = uri;
        imageView.setTag(uri);
        this.imageUriMd5 = Md5Utils.getMd5(uri);

        //
//        reqWidth = getImageViewWidth();
//        reqHeight = getImageViewHeight();
    }

    public ImageView getImageView() {
        return mImageViewRef.get();
    }

    public int getImageViewWidth() {
        return ImageViewUitls.getImageViewWidth(mImageViewRef.get());
    }

    public int getImageViewHeight() {
        return ImageViewUitls.getImageViewHeight(mImageViewRef.get());
    }

    public BitmapRequest setLoadPolicy(LoadPolicy mLoadPolicy) {
        this.mLoadPolicy = mLoadPolicy;
        return this;
    }

    /**
     * tag url
     *
     * @return
     */
    public boolean isImageViewTagValid() {
        return mImageViewRef.get() != null ? mImageViewRef.get().getTag().equals(imageUri) : false;
    }

    @Override
    public int compareTo(BitmapRequest another) {
        return mLoadPolicy.compare(this, another);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BitmapRequest request = (BitmapRequest) o;

        if (serialNum != request.serialNum) return false;
        if (mImageViewRef != null ? !mImageViewRef.get().equals(request.mImageViewRef.get()) : request.mImageViewRef != null)
            return false;
        return imageUri.equals(request.imageUri);

    }

    @Override
    public int hashCode() {
        int result = mImageViewRef != null ? mImageViewRef.get().hashCode() : 0;
        result = 31 * result + imageUri.hashCode();
        result = 31 * result + serialNum;
        return result;
    }
}
