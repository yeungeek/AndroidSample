package com.yeungeek.dagger2sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.yeungeek.dagger2sample.network.GithubApiInterface;
import com.yeungeek.dagger2sample.network.models.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Inject
    OkHttpClient mOkHttpClient;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    GithubApiInterface githubApiInterface;

    private TextView mResult;

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

        mResult = (TextView) findViewById(R.id.result);

        ((SampleApp) getApplication()).getGitHubComponent().inject(this);

        sendRequest();
    }

    private void sendRequest() {
        Call<ArrayList<Repository>> call = githubApiInterface.getRepository("square");
        call.enqueue(new Callback<ArrayList<Repository>>() {
            @Override
            public void onResponse(Response<ArrayList<Repository>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<Repository> repos = response.body();
                    mResult.setText(Arrays.deepToString(repos.toArray()));
                } else {
                    try {
                        mResult.setText(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
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
}
