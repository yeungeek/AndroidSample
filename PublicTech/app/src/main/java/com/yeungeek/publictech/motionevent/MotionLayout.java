package com.yeungeek.publictech.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by yeungeek on 2016/6/30.
 */

public class MotionLayout extends LinearLayout {
    public MotionLayout(Context context) {
        super(context);
    }

    public MotionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("DEBUG","#### viewGroup dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
//        return true;
    }
}
