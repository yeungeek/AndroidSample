package com.yeungeek.imageloadersample.custom.v2.imageloader.config;

import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.BitmapCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.MemoryCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.policy.LoadPolicy;
import com.yeungeek.imageloadersample.custom.v2.imageloader.policy.SerialPolicy;

/**
 * @author yeungeek
 */
public class ImageLoaderConfig {
    /**
     * cache
     */
    public BitmapCache bitmapCache = new MemoryCache();
    /**
     * display
     */
    public DisplayConfig displayConfig = new DisplayConfig();
    /**
     * policy
     */
    public LoadPolicy loadPolicy = new SerialPolicy();
    /**
     * thread count
     */
    public int threadCount = Runtime.getRuntime().availableProcessors() + 1;

    public ImageLoaderConfig setBitmapCache(BitmapCache bitmapCache) {
        this.bitmapCache = bitmapCache;
        return this;
    }

    public ImageLoaderConfig setLoadingPlaceholder(int resId) {
        displayConfig.loadingResId = resId;
        return this;
    }

    public ImageLoaderConfig setEmptydPlaceholder(int resId) {
        displayConfig.failedResId = resId;
        return this;
    }

    public ImageLoaderConfig setLoadPolicy(LoadPolicy loadPolicy) {
        this.loadPolicy = loadPolicy;
        return this;
    }

    public ImageLoaderConfig setThreadCount(int threadCount) {
        this.threadCount = Math.max(1, threadCount);
        return this;
    }
}
