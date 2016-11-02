package com.estsoft.muvigram.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 25..
 */

public class TagProfileActivity extends BasePlainActivity {

    DisplayMetrics mMetrics;
    private Integer[] mTagThum = {R.drawable.profile_test, R.drawable.profile_test1,
            R.drawable.profile_test2, R.drawable.profile_test3, R.drawable.profile_test};

    @BindView(R.id.tag_gridview) ExpandableHeightGridView mGridView;
    @BindView(R.id.popular_button) Button mPopularButton;
    @BindView(R.id.recent_button) Button mRecentButton;

    @OnClick(R.id.back_button) void clickBackButton(){
        finish();
    }
    @OnClick(R.id.popular_button) void clickPopularButton(){
        mPopularButton.setBackgroundResource(R.color.white);
        mPopularButton.setTextColor(getResources().getColor(R.color.light_blue_300));
        mRecentButton.setBackgroundResource(R.color.gray);
        mRecentButton.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick(R.id.recent_button) void clickRecentButton(){
        mPopularButton.setBackgroundResource(R.color.gray);
        mPopularButton.setTextColor(getResources().getColor(R.color.white));
        mRecentButton.setBackgroundResource(R.color.white);
        mRecentButton.setTextColor(getResources().getColor(R.color.light_blue_300));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_profile);
        ButterKnife.bind(this);

        mGridView.setExpanded(true);
        mGridView.setAdapter(new ImageAdapter(this));
//        gridView.setOnItemClickListener(gridViewOnItemClickListener);
        mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mTagThum.length;
        }

        public Object getItem(int position) {
            return mTagThum[position];
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            int rowWidth = (mMetrics.widthPixels) / 3;

            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(rowWidth,rowWidth));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mTagThum[position]);
            return imageView;
        }
    }
}
