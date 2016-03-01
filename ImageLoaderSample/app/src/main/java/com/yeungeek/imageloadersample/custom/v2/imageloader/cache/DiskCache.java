package com.yeungeek.imageloadersample.custom.v2.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapDecoder;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;
import com.yeungeek.imageloadersample.custom.util.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class DiskCache implements BitmapCache {
    private static final String IMAGE_DISK_CACHE = "v2";

    private DiskLruCache mDiskLruCache;
    private static DiskCache mDiskCache;

    public DiskCache(final Context context) {
        initDiskCache(context);
    }

    public static DiskCache getDiskCache(final Context context) {
        if (null == mDiskCache) {
            synchronized (DiskCache.class) {
                if (null == mDiskCache) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }

        return mDiskCache;
    }

    private void initDiskCache(final Context context) {
        try {
            File cacheDir = getDiskCacheDir(context, IMAGE_DISK_CACHE);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, 50 * 1024 * 1204);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDiskCacheDir(final Context context, final String name) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.d("DEBUG", "### init cache dir: " + context.getExternalCacheDir());
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath, name);
    }

    @Override
    public synchronized Bitmap get(final BitmapRequest key) {
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                final InputStream inputStream = getInputStream(key.imageUriMd5);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                IOUtils.closeQuietly(inputStream);
                return bitmap;
            }
        };

        return decoder.decodeBitmap(key.reqWidth, key.reqHeight);
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        //
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(key.imageUriMd5);
            if (null != editor) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (writeBitmapToDisk(bitmap, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }

                IOUtils.closeQuietly(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(BitmapRequest key) {
        try {
            return mDiskLruCache.remove(key.imageUriMd5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private InputStream getInputStream(final String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (null != snapshot) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean writeBitmapToDisk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8 * 1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos); //TODO
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.closeQuietly(bos);
        }
        return true;
    }
}
