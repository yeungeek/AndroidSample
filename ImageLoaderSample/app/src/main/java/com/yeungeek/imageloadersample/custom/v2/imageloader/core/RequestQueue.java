package com.yeungeek.imageloadersample.custom.v2.imageloader.core;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yeungeek on 2016/2/11.
 */
public final class RequestQueue {
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<BitmapRequest>();

    /**
     * 请求的序列化生成器
     */
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors() + 1;
    /**
     * CPU核心数 + 1个分发线程数
     */
    private int mDispatcherNums = DEFAULT_CORE_NUMS;
    private ImageDispatcher[] mDispatchers = null;

    protected RequestQueue() {
        this(DEFAULT_CORE_NUMS);
    }

    protected RequestQueue(int coreNums) {
        mDispatcherNums = coreNums;
    }

    public void start() {
        stop();
        startNetworkExeutors();
    }

    private void startNetworkExeutors() {
        mDispatchers = new ImageDispatcher[mDispatcherNums];
        for (int i = 0; i < mDispatchers.length; i++) {
            Log.e("DEBUG", "### start dispacher: " + i);
            mDispatchers[i] = new ImageDispatcher(mRequestQueue);
            mDispatchers[i].start();
        }
    }

    public void stop() {
        if (null != mDispatchers && mDispatchers.length > 0) {
            for (ImageDispatcher dispacher : mDispatchers) {
                dispacher.interrupt();
            }
        }
    }

    public void clear() {
        mRequestQueue.clear();
    }

    public void addRequest(BitmapRequest request) {
        if (!mRequestQueue.contains(request)) {
            request.serialNum = this.generateSerialNumber();
            mRequestQueue.add(request);
        } else {
            Log.e("DEBUG", "### queue contains");
        }
    }

    public BlockingQueue<BitmapRequest> getAllRequests() {
        return mRequestQueue;
    }

    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
