package com.yeungeek.publictech.motionevent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    @BindView(R.id.id_event_img)
    ImageView iv;
    @BindView(R.id.id_motion_view)
    View motionView;

    @BindView(R.id.id_motion_layout)
    LinearLayout motionLayout;
    @BindView(R.id.id_event_btn3)
    Button btn3;
    @BindView(R.id.id_event_btn4)
    Button btn4;
    @BindView(R.id.id_inner_motion_view)
    View innerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "#### btn1 onClick");
            }
        });

        btn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "#### btn1 onTouch=" + event.getAction());
//                return false;
                return false;
            }
        });

        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "#### imageView onTouch=" + event.getAction());
//                return false;
                return true;
            }
        });

        motionView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "#### motionView onTouch=" + event.getAction());
//                return false;
                return true;
            }
        });

        //viewGroup
        motionLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "#### motionLayout onTouch=" + event.getAction());
                return false;
            }
        });

        btn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "#### btn3 onTouch: " + event.getAction());
                return false;
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "#### btn3 onClick");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "#### btn4 onClick");
//                if(true && test()){
//                    Log.d("DEBUG","#### test execute");
//                }
            }
        });

        innerView.setClickable(true);
        innerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("DEBUG", "#### innerView onTouch: " + event.getAction());
                return false;
            }
        });
    }

    private boolean test() {
        Log.d("DEBUG", "#### test");
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("DEBUG", "#### activity dispatchTouchEvent: " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected int getResId() {
        return R.layout.activity_motion_event;
    }
}
