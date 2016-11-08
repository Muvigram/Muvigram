package com.estsoft.muvigram.ui.videoselect;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.model.VideoMetaData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by estsoft on 2016-11-03.
 */

@PerSingleFragment
public class ThumbnailListAdapter extends BaseAdapter {
    private static final String TAG = "ThumbnailListAdaptor";

//    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<VideoMetaData> mVideoMetaDatas;

    @Inject
    public ThumbnailListAdapter(@ActivityContext Context context) {
//        mContext = context;
        mInflater = LayoutInflater.from(context);
        mVideoMetaDatas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mVideoMetaDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        Bitmap thumbBitmap = mVideoMetaDatas.get(i).getScaledBitmap();
        int runtime = mVideoMetaDatas.get(i).getRuntime();
        if (view == null) {
            view = mInflater.inflate(R.layout.video_select_item, viewGroup, false);
            viewHolder = new ViewHolder( view );
            view.setTag( viewHolder );
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.thumbnailImageView.setImageBitmap(thumbBitmap);
        viewHolder.runtimeTextView.setText(" " + runtime + "\" ");

        return view;
    }

    public void addVideoMetaData( VideoMetaData videoMetaData ) {
        Log.d(TAG, "addVideoMetaData: " + videoMetaData);
        mVideoMetaDatas.add(videoMetaData);
        notifyDataSetChanged();
    }

    public int getRuntimeAt( int position ) {
        return mVideoMetaDatas.get(position).getRuntime();
    }
    public String getVideoPath( int position ) {
        return mVideoMetaDatas.get(position).getVideoPath();
    }

    class ViewHolder {
        @BindView(R.id.select_thumbnail_image)ImageView thumbnailImageView;
        @BindView(R.id.select_video_runtime)TextView runtimeTextView;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
