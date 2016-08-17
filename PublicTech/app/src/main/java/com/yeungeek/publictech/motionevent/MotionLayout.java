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
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### ViewGroup dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### ViewGroup dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### ViewGroup dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("DEBUG", "#### ViewGroup dispatchTouchEvent ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### ViewGroup onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### ViewGroup onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### ViewGroup onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("DEBUG", "#### ViewGroup onTouchEvent ACTION_CANCEL");
                break;
        }

        boolean result = super.onTouchEvent(event);
        Log.d("DEBUG", "#### ViewGroup onTouchEvent Result: " + result);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### ViewGroup onInterceptTouchEvent ACTION_DOWN");
//                intercept = true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### ViewGroup onInterceptTouchEvent ACTION_MOVE");
                intercept = true;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### ViewGroup onInterceptTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("DEBUG", "#### ViewGroup onInterceptTouchEvent ACTION_CANCEL");
                break;
        }

//        intercept = true;
        Log.d("DEBUG", "#### ViewGroup onInterceptTouchEvent Result: " + intercept);
        return intercept;
    }
}
