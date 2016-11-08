package com.estsoft.muvigram.ui.videoselect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.VideoMetaData;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.videocut.VideoCutActivity;
import com.estsoft.muvigram.ui.videoedit.VideoEditActivity;
import com.estsoft.muvigram.util.DialogFactory;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoSelectFragment extends BaseSingleFragment implements VideoSelectView {
    
    private static final String TAG = "VideoSelectFragment";
    private static final int MIN_DURATION = 15;
    private static final int MAX_DURATION = 180;

    @Inject VideoSelectPresenter mPresenter;
    @Inject ThumbnailListAdapter mThumbnailAdaptor;

    @BindView(R.id.select_video_grid)  GridView mGridView;
    @BindView(R.id.select_video_layout)   LinearLayout mDisableLayout;
    @BindView(R.id.select_video_progress)   ProgressBar mProgressBar;
    @BindView(R.id.select_video_back_image_view)    ImageView mBackButton;

    @OnTouch(R.id.select_video_layout)
    boolean onLayoutTouch() { return true; }

    @OnClick(R.id.select_video_back_image_view)
    void onBackClick() {
        getActivity().onBackPressed();
    }

    @OnItemClick(R.id.select_video_grid)
    void onGridViewClick(int position) {
        int selectedVideoRuntime = mThumbnailAdaptor.getRuntimeAt(position);
        if ( selectedVideoRuntime <= 0 ) {
            Toast.makeText(getContext(), getString(R.string.video_select_force_wait), Toast.LENGTH_SHORT).show();
        } else if ( selectedVideoRuntime < MIN_DURATION ) {
            String warningMsg = getString(R.string.video_select_too_short).replace("MIN_TIME", String.valueOf(MIN_DURATION));
            Toast.makeText(getContext(), warningMsg, Toast.LENGTH_SHORT).show();
        } else if ( selectedVideoRuntime > MAX_DURATION ) {
            String warningMsg = getString(R.string.video_select_too_long).replace("MAX_TIME", String.valueOf(MAX_DURATION));
            Toast.makeText(getContext(), warningMsg, Toast.LENGTH_SHORT).show();
        } else {
            nextActivity( mThumbnailAdaptor.getVideoPath(position) );
        }
    }

    private Unbinder mUnbinder;

    public static VideoSelectFragment newInstance() {
        return new VideoSelectFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_select, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGridView.setAdapter(mThumbnailAdaptor);
        mPresenter.loadThumbnails();
    }

    /*View logic here ... */

    @Override
    public void updateThumbnails(VideoMetaData metaData) {
        mThumbnailAdaptor.addVideoMetaData(metaData);
    }

    @Override
    public void enableProgress() {
        mDisableLayout.setVisibility(View.VISIBLE);
        Log.d(TAG, "enableProgress: ");
    }

    @Override
    public void disableProgress() {
        mDisableLayout.setVisibility(View.GONE);
        Log.d(TAG, "disableProgress: ");
    }

    @Override
    public void nextActivity(String path) {
        Intent intent = VideoCutActivity.newIntent(getContext(), path);
        startActivity(intent);
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(
                getActivity(),
                getString(R.string.video_select_local_loading_error)
        ).show();
        getActivity().finish();
    }

}

