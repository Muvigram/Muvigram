package com.estsoft.muvigram.ui.videoedit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.videocut.VideoCutPresenter;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoEditFragment extends BaseSingleFragment implements VideoEditView {

    private static final String TAG = "VideoEditFragment";
    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoEditFragment.video_path";

    @Inject VideoEditPresenter mPresenter;

    String mCutVideoPath;

    public static VideoEditFragment newInstance(String videoPath) {
        Bundle args = new Bundle();
        args.putString( ARG_VIDEO_PATH, videoPath );
        VideoEditFragment videoEditFragment = new VideoEditFragment();
        videoEditFragment.setArguments(args);
        return videoEditFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCutVideoPath = getArguments().getString(ARG_VIDEO_PATH);

        Log.d(TAG, "onCreate: " + mCutVideoPath);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    /* View logic here ... */
    @Override
    public void onStart() {
        super.onStart();
    }

}
