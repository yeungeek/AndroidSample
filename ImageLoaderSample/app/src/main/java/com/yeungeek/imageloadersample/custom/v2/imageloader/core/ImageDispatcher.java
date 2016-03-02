package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.util.Log;

import com.yeungeek.imageloadersample.custom.v2.imageloader.loader.Loader;
import com.yeungeek.imageloadersample.custom.v2.imageloader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by yeungeek on 2016/2/11.
 */
final class ImageDispatcher extends Thread {
    private BlockingQueue<BitmapRequest> mRequestQueue;

    public ImageDispatcher(final BlockingQueue<BitmapRequest> queue) {
        this.mRequestQueue = queue;
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                final BitmapRequest request = mRequestQueue.take();
                if (request.isCancel) {
                    continue;
                }

                final String schema = parseSchema(request.imageUri);
                Loader loader = LoaderManager.instance().getLoader(schema);
                loader.loadImage(request);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String parseSchema(String imageUri) {
        if (imageUri.contains("://")) {
            return imageUri.split("://")[0];
        } else {
            Log.e(getName(), "### wrong scheme, image uri: " + imageUri);
        }
        return "";
    }
}
