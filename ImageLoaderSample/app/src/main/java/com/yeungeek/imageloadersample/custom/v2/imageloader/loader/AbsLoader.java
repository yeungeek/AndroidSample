package com.yeungeek.imageloadersample.custom.v2.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.BitmapCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.config.DisplayConfig;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.SimpleImageLoader;

/**
 * Created by yeungeek on 2016/2/11.
 */
public abstract class AbsLoader implements Loader {
    private static BitmapCache mCache = SimpleImageLoader.instance().getConfig().bitmapCache;

    @Override
    public void loadImage(BitmapRequest request) {
        Bitmap bitmap = mCache.get(request);
        Log.d("DEBUG", "### cache bitmap: " + bitmap);
        if (null == bitmap) {
            showLoading(request);
            bitmap = onLoadImage(request);
            cacheBitmap(request, bitmap);
        } else {
            request.justCacheInMem = true;
        }

        deliveryUIThread(request, bitmap);
    }

    private void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid() && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(request.displayConfig.loadingResId);
                }
            });
        }
    }

    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (null != bitmap && null != mCache) {
            synchronized (mCache) {
                mCache.put(request, bitmap);
            }
        }
    }

    private void deliveryUIThread(final BitmapRequest request, final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (null == imageView) {
            return;
        }

        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        final String uri = request.imageUri;
        // tag
        if (null != bitmap && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(bitmap);
        }

        if (null == bitmap && hasFaildPlaceholder(request.displayConfig)) {
            imageView.setImageResource(request.displayConfig.failedResId);
        }

        //listener
        if (null != request.imageListener) {
            request.imageListener.onComplete(imageView, bitmap, uri);
        }
    }

    protected abstract Bitmap onLoadImage(BitmapRequest request);

    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.loadingResId > 0;
    }

    private boolean hasFaildPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }
}
