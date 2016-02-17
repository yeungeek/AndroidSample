package com.yeungeek.imageloadersample.custom.v2.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class LoaderManager {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";

    private Map<String, Loader> mLoaderMap = new HashMap<>();
    private static LoaderManager mInstance;

    private LoaderManager() {
        register(FILE, new LocalLoader());
        register(HTTP, new UrlLoader());
        register(HTTPS, new UrlLoader());
    }

    public static LoaderManager instance() {
        if (null == mInstance) {
            synchronized (LoaderManager.class) {
                if (null == mInstance) {
                    mInstance = new LoaderManager();
                }
            }
        }

        return mInstance;
    }

    public final synchronized void register(final String schema, final Loader loader) {
        mLoaderMap.put(schema, loader);
    }

    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }

        return new UrlLoader();//default
    }
}
