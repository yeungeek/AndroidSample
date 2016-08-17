package com.yeungeek.publictech.motionevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import com.yeungeek.publictech.BaseActivity;
import com.yeungeek.publictech.R;

/**
 * Created by yeungeek on 2016/6/28.
 */

public class MotionEventActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### Activity dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### Activity dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### Activity dispatchTouchEvent ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "#### Activity onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "#### Activity onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("DEBUG", "#### Activity dispatchTouchEvent ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_motion_event;
    }
}
