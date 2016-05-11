package com.yeungeek.publictech;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTest = (TextView) findViewById(R.id.id_test);

        mTest.setText(new SimplePojo("s1,", "s2").toString());
    }
}
