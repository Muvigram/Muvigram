package com.estsoft.muvigram.ui.videocut;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.EditVideoView;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.videoedit.VideoEditActivity;
import com.estsoft.muvigram.ui.videoedit.VideoEditFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoCutFragment extends BaseSingleFragment implements VideoCutView {
    private static final String TAG = "VideoCutFragment";
    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoCutFragment.video_path";

    @Inject VideoCutPresenter mPresenter;

    @BindView(R.id.edit_video_thumbnail_scroll_view)    HorizontalScrollView mScrollView;
    @BindView(R.id.edit_video_thumbnail_container_layout)    LinearLayout mThumbnailContainer;
    @BindView(R.id.edit_video_video_view)    EditVideoView mVideoView;
    @BindView(R.id.edit_video_submit_button)    Button mSubmitButton;
    @BindView(R.id.edit_video_thumbnail_progressbar)    ProgressBar mProgressBar;
    @BindView(R.id.edit_video_disable_layout)               LinearLayout mDisableLayout;

    @OnTouch(R.id.edit_video_thumbnail_scroll_view)
    boolean OnScrollTouch(MotionEvent motionEvent){
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            mVideoOffsetMs
                    = (int)((float)((mScrollView.getScrollX() * CUT_STANDARD_MILLISECOND) / mWindowWidth));
            mVideoView.reStartAtMs(mVideoOffsetMs);
            Log.d(TAG, "OnScrollTouch: " + mVideoOffsetMs);
        }
        return false;
    }
    @OnClick(R.id.edit_video_submit_button)
    void OnSubmitClick( View view ) {
        mPresenter.cutVideo( mVideoPath, mVideoOffsetMs, CUT_STANDARD_MILLISECOND / MILLI_WEIGHT);
    }

    private Unbinder mUnbinder;
    String mVideoPath;
    int mThumbnailContainerHeight;
    int mWindowWidth;
    int mThumbnailInterval;
    int mVideoDuration;
    int mVideoOffsetMs;

    public static VideoCutFragment newInstance(String videoPath) {
        Bundle args = new Bundle();
        args.putString( ARG_VIDEO_PATH, videoPath );
        VideoCutFragment videoEditFragment = new VideoCutFragment();
        videoEditFragment.setArguments(args);
        return videoEditFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoPath = getArguments().getString(ARG_VIDEO_PATH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_cut, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        mThumbnailContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mThumbnailContainer.getViewTreeObserver().removeOnGlobalLayoutListener( this );
                checkLayoutMeasured();
            }
        });

        return view;
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

    // real start time of this fragment
    @Override
    public void checkLayoutMeasured() {
        mThumbnailContainerHeight = mThumbnailContainer.getMeasuredHeight();
        Point displaySize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        mWindowWidth = displaySize.x;
        mThumbnailInterval
                = Math.round(CUT_STANDARD_MILLISECOND / (((float)mWindowWidth / (float)mThumbnailContainerHeight)));
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mVideoPath);
        mVideoDuration = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        mVideoView.init( CUT_STANDARD_MILLISECOND, mVideoDuration );
        mVideoView.setVideoPath(mVideoPath);
        mVideoView.start();

        mPresenter.updateSplitThumbnails(mVideoPath, mVideoDuration, mThumbnailInterval, mThumbnailContainerHeight);
    }

    @Override
    public void updateThumbnails(Bitmap bitmap) {
        mVideoView.pauseView();
        ImageView imageView = new ImageView(getActivity());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(bitmap);
        mThumbnailContainer.addView(imageView);
    }

    @Override
    public void enableProgress() {
        Log.d(TAG, "enableProgress: ");
    }

    @Override
    public void disableProgress() {
        mVideoView.reStartAtMs( 0 );
        Log.d(TAG, "disableProgress: ");
    }

    @Override
    public void nextActivity(String cutAudioPath) {
        Intent intent = VideoEditActivity.newIntent(getContext(), cutAudioPath);
        startActivity(intent);
    }

    @Override
    public void onError() {
        Log.d(TAG, "onError: ");
    }
}
