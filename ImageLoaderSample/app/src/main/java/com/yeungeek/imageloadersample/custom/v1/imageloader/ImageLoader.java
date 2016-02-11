package com.yeungeek.imageloadersample.custom.v1.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.disklrucache.DiskLruCache;
import com.yeungeek.imageloadersample.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yeungeek on 2016/2/9.
 * <p>
 * DiskLruCache use <a href="https://github.com/JakeWharton/DiskLruCache">https://github.com/JakeWharton/DiskLruCache</a>
 * </p>
 */
public class ImageLoader {
    private ImageLoader(final Context context) {
        mContext = context.getApplicationContext();

        int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemorySize / 8;
        mMemeoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File cacheDir = getDiskCache(mContext, "custom-v1");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        try {
            //getUsableSpace
            mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDiskCache(final Context context, final String diskName) {
        boolean external = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (external) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + diskName);
    }

    public static ImageLoader build(final Context context) {
        return new ImageLoader(context);
    }

    public void displayImage(final String uri, final ImageView imageView, final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        //1 step: get from memory cache
        Bitmap bitmap = loadBitmapFromMemory(uri);
        if (null != bitmap) {
            Log.d("DEBUG", "[displayImage] load bitmap from memory with uri: " + uri);
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                //2 step get from local or network
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (null != bitmap) {
                    LoadResult result = new LoadResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    private Bitmap loadBitmap(final String url, final int reqWidth, final int reqHeight) {
        //1. from memory
        Bitmap bitmap = loadBitmapFromMemory(url);
        if (null != bitmap) {
            Log.d("DEBUG", "[loadBitmap] load bitmap from memory,url: " + url);
            return bitmap;
        }

        //2. from disk
        try {
            bitmap = loadBitmapFromDisk(url, reqWidth, reqHeight);
            if (null != bitmap) {
                Log.d("DEBUG", "load bitmap from disk,url: " + url);
                return bitmap;
            }

            bitmap = loadBitmapFromHttp(url, reqWidth, reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (null == bitmap) {
//            Log.d("DEBUG", "load bitmap from disk,url: " + url);
//        }

        return bitmap;
    }

    private Bitmap loadBitmapFromHttp(final String url, final int reqWidth, final int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can't run on main UI thread");
        }

        if (null == mDiskLruCache) {
            return null;
        }

        String key = md5Key(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (null != editor) {
            OutputStream outputStream = editor.newOutputStream(0);
            if (downloadUrlToStream(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }

        return loadBitmapFromDisk(url, reqWidth, reqHeight);
    }

    private boolean downloadUrlToStream(final String urlString, final OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != urlConnection) {
                urlConnection.disconnect();
            }

            try {
                if (null != out) {
                    out.close();
                }

                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private Bitmap loadBitmapFromDisk(final String url, final int reqWidth, final int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.d("DEBUG", "load bitmap from UI Thread, it's not recommended!");
        }

        if (null == mDiskLruCache) {
            return null;
        }

        Bitmap bitmap = null;
        final String key = md5Key(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (null != snapshot) {
            FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(0);

            bitmap = mImageResizer.decodeBitmapFromFile(inputStream.getFD(), reqWidth, reqHeight);
            if (null != bitmap) {
                addBitmapToMemory(key, bitmap);
            }
        }

        return bitmap;
    }

    private Bitmap loadBitmapFromMemory(final String url) {
        final String key = md5Key(url);
        return getBitmapFromMemory(key);
    }

    private String md5Key(final String url) {
        String cacheKey;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            cacheKey = bytesToHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = "";
        }

        return cacheKey;
    }

    /**
     * memory
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromMemory(final String key) {
        return mMemeoryCache.get(key);
    }

    private void addBitmapToMemory(final String key, final Bitmap bitmap) {
        if (null == getBitmapFromMemory(key)) {
            mMemeoryCache.put(key, bitmap);
        }
    }

    private String bytesToHex(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                builder.append("0");
            }

            builder.append(hex);
        }

        return builder.toString();
    }

    private static class LoadResult {
        public ImageView imageView;
        public String url;
        public Bitmap bitmap;

        public LoadResult(final ImageView imageView, final String url, final Bitmap bitmap) {
            this.imageView = imageView;
            this.url = url;
            this.bitmap = bitmap;
        }
    }

    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_POST_RESULT) {
                LoadResult result = (LoadResult) msg.obj;
                ImageView imageView = result.imageView;
                final String url = (String) imageView.getTag(TAG_KEY_URI);
                if (url.equals(result.url)) {
                    imageView.setImageBitmap(result.bitmap);
                } else {
                    Log.w("DEBUG", "set image bitmap,url has changed,ignored");
                }
            } else {
                Log.d("DEBUG", "receive msg code: " + msg.what);
            }
        }
    };

    public static final int MESSAGE_POST_RESULT = 1;

    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int IO_BUFFER_SIZE = 8 * 1024;

    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger count = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + count.getAndIncrement());
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(), threadFactory
    );

    private ImageResizer mImageResizer = new ImageResizer();

    private Context mContext;
    private LruCache<String, Bitmap> mMemeoryCache;
    private DiskLruCache mDiskLruCache;
}
