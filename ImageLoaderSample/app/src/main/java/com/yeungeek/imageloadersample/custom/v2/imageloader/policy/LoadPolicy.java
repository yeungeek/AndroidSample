package com.yeungeek.imageloadersample.custom.v2.imageloader.policy;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;

/**
 * Created by yeungeek on 2016/2/11.
 */
public interface LoadPolicy {
    public int compare(BitmapRequest one, BitmapRequest another);
}
