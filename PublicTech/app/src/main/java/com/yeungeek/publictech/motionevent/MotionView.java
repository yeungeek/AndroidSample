package com.yeungeek.publictech.motionevent;

import android.content.Context;
import android.os.Build;
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### View dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### View dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### View dispatchTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("DEBUG", "#### View dispatchTouchEvent ACTION_CANCEL");
                break;
        }

        boolean result = super.dispatchTouchEvent(event);
        Log.d("DEBUG", "#### View dispatchTouchEvent Result: " + result);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### View onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### View onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### View onTouchEvent ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d("DEBUG", "#### View onTouchEvent ACTION_CANCEL");
                break;
        }

        boolean result = super.onTouchEvent(event);
        Log.d("DEBUG", "#### View onTouchEvent Result: " + result);
        return result;
    }

    @Override
    public boolean performClick() {
        Log.d("DEBUG", "#### View performClick");
        return super.performClick();
    }
}
