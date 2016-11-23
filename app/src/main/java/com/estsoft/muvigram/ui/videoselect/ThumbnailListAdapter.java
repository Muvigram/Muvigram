package com.estsoft.muvigram.ui.videoselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.model.VideoMetaData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by estsoft on 2016-11-03.
 */

@PerSingleFragment
public class ThumbnailListAdapter extends RecyclerView.Adapter<ThumbnailListAdapter.ViewHolder>{
    private static final String TAG = "ThumbnailListAdaptor";

    private final Context mContext;
    private final LayoutInflater mInflater;
    private List<VideoMetaData> mVideoMetaDatas;
    private View.OnClickListener mHolderClickListener;

    @Inject
    public ThumbnailListAdapter(@ActivityContext Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mVideoMetaDatas = new ArrayList<>();
    }

    public void setOnHolderClickListener( View.OnClickListener listener ) {
        mHolderClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mVideoMetaDatas.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.thumbnailImageView.setImageBitmap( mVideoMetaDatas.get(position).getScaledBitmap() );
        holder.runtimeTextView.setText(" " + mVideoMetaDatas.get(position).getRuntime() +"\"" );
        holder.setParams( mVideoMetaDatas.get(position).getVideoPath(), mVideoMetaDatas.get(position).getRuntime() );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video_select_item, parent, false);
        view.setOnClickListener( mHolderClickListener );
        ViewHolder viewHolder = new ViewHolder( view );
        return viewHolder;
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

    class ViewHolder extends RecyclerView.ViewHolder {
        String videoPath;
        int runtime;
        @BindView(R.id.select_thumbnail_image)ImageView thumbnailImageView;
        @BindView(R.id.select_video_runtime)TextView runtimeTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            thumbnailImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        void setParams(String path, int runtime){
            this.videoPath = path;
            this.runtime = runtime;
        }
    }
}
