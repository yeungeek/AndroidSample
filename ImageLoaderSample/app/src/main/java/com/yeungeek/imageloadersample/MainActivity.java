package com.yeungeek.imageloadersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yeungeek.imageloadersample.custom.v1.V1Activity;
import com.yeungeek.imageloadersample.custom.v2.V2Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mImageLoaderV1;
    private Button mImageLoaderV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        mImageLoaderV1 = (Button) findViewById(R.id.image_loader_v1);
        mImageLoaderV2 = (Button) findViewById(R.id.image_loader_v2);
        mImageLoaderV1.setOnClickListener(this);
        mImageLoaderV2.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_loader_v1:
                startActivity(new Intent(MainActivity.this, V1Activity.class));
                break;
            case R.id.image_loader_v2:
                startActivity(new Intent(MainActivity.this, V2Activity.class));
                break;
        }
    }
}
