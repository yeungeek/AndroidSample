package com.yeungeek.publictech.motionevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yeungeek on 2016/6/30.
 */

public class MotionView extends View {
    public MotionView(Context context) {
        super(context);
    }

    public MotionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MotionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("DEBUG", "#### view dispatchTouchEvent: " + event.getAction());
        boolean result = super.dispatchTouchEvent(event);
//        Log.d("DEBUG", "#### view dispatchTouchEvent: " + event.getAction());
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("DEBUG", "#### view onTouchEvent: " + event.getAction());
        boolean result = super.onTouchEvent(event);
//        Log.d("DEBUG", "#### after onTouchEvent: " + event.getAction());
        return result;
    }

    @Override
    public boolean performClick() {
        Log.d("DEBUG", "#### view performClick: ");
        boolean result = super.performClick();
//        Log.d("DEBUG", "#### after performClick: ");
        return result;
    }
}
