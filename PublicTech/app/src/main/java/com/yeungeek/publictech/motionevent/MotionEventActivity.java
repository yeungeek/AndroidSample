package com.yeungeek.publictech.motionevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.yeungeek.publictech.BaseActivity;
import com.yeungeek.publictech.R;

import butterknife.BindView;

/**
 * Created by yeungeek on 2016/6/28.
 */

public class MotionEventActivity extends BaseActivity {
    @BindView(R.id.id_event_btn1)
    Button btn1;
    @BindView(R.id.id_event_btn2)
    Button btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG","#### btn1 onClick");
            }
        });

        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG","#### btn1 onTouch=" + event.getAction());
//                return false;
                return true;
            }
        });
    }

    @Override
    protected int getResId() {
        return R.layout.activity_motion_event;
    }
}
