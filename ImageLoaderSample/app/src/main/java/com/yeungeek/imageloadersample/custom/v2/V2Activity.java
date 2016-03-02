package com.yeungeek.imageloadersample.custom.v2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yeungeek.imageloadersample.R;
import com.yeungeek.imageloadersample.custom.base.BaseActivity;
import com.yeungeek.imageloadersample.custom.util.AndroidUtils;
import com.yeungeek.imageloadersample.custom.v1.Constants;
import com.yeungeek.imageloadersample.custom.v2.imageloader.cache.DoubleCache;
import com.yeungeek.imageloadersample.custom.v2.imageloader.config.ImageLoaderConfig;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.SimpleImageLoader;
import com.yeungeek.imageloadersample.custom.v2.imageloader.policy.ReversePolicy;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yeungeek on 2016/2/16.
 */
public class V2Activity extends BaseActivity {
    private GridView mGridView;
    private GridAdapter mAdapter;
    private List<String> mDatas;
    private int mImageWidth = 0;
    private SimpleImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v2);

        initImageLoader();
        initData();
        initView();
    }

    private void initImageLoader() {
        ImageLoaderConfig config = new ImageLoaderConfig()
                .setLoadingPlaceholder(R.mipmap.ic_launcher)
                .setBitmapCache(new DoubleCache(this))
                .setLoadPolicy(new ReversePolicy())
                .setThreadCount(4);

        mImageLoader = SimpleImageLoader.instance();
        mImageLoader.init(config);
    }

    private void initData() {
        mDatas = Arrays.asList(Constants.urls);

        int screenWidth = AndroidUtils.getScreenMetrics(this).widthPixels;
        int space = (int) AndroidUtils.dp2px(this, 20f);
        mImageWidth = (screenWidth - space) / 3;
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gridview);

        mAdapter = new GridAdapter(this);
        mGridView.setAdapter(mAdapter);
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

            mImageLoader.displayImage(imageView,url);
            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView imageView;
    }
}
