package com.yeungeek.imageloadersample.custom.v2.imageloader.policy;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class ReversePolicy implements LoadPolicy {
    @Override
    public int compare(BitmapRequest one, BitmapRequest another) {
        return another.serialNum - one.serialNum;
    }
}
