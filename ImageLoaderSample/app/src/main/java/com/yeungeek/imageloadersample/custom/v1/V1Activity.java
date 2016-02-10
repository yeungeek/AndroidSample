package com.yeungeek.imageloadersample.custom.v1;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yeungeek.imageloadersample.R;
import com.yeungeek.imageloadersample.custom.base.BaseActivity;
import com.yeungeek.imageloadersample.custom.v1.imageloader.ImageLoader;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yeungeek on 2016/2/9.
 */
public class V1Activity extends BaseActivity implements AbsListView.OnScrollListener {
    private GridView mGridView;
    private GridAdapter mAdapter;
    private List<String> mDatas;
    private ImageLoader mImageLoader;
    private int mImageWidth = 0;
    private boolean mIsGridViewIdle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v1);

        initData();
        initView();
        mImageLoader = ImageLoader.build(this);
    }

    private void initData() {
        mDatas = Arrays.asList(Constants.urls);

        int screenWidth = getScreenMetrics(this).widthPixels;
        int space = (int) dp2px(this, 20f);
        mImageWidth = (screenWidth - space) / 3;
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setOnScrollListener(this);

        mAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            mIsGridViewIdle = true;
            mAdapter.notifyDataSetChanged();
        } else {
            mIsGridViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class GridAdapter extends BaseAdapter {
        private final Context mContext;

        public GridAdapter(final Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public String getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null, false);
                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String url = getItem(position);
            ImageView imageView = holder.imageView;
            String tag = (String) imageView.getTag();

            if (!url.equals(tag)) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            if (mIsGridViewIdle) {
                imageView.setTag(url);
                mImageLoader.displayImage(url, imageView, mImageWidth, mImageWidth);
            }

            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

}
