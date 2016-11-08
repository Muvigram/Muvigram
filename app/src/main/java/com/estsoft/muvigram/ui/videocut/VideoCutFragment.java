package com.estsoft.muvigram.ui.videocut;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
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
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.videoedit.VideoEditActivity;

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

    @BindView(R.id.cut_video_thumbnail_scroll_view)    HorizontalScrollView mScrollView;
    @BindView(R.id.cut_video_thumbnail_container_layout)    LinearLayout mThumbnailContainer;
    @BindView(R.id.cut_video_video_view)    IncreasVideoView mVideoView;
    @BindView(R.id.cut_video_submit_button)    ImageView mSubmitButton;
    @BindView(R.id.cut_video_thumbnail_progressbar)    ProgressBar mProgressBar;
    @BindView(R.id.cut_video_disable_layout)               LinearLayout mDisableLayout;
    @BindView(R.id.cut_video_back_image_view) ImageView mBackButton;

    @OnTouch(R.id.cut_video_disable_layout)
    boolean onLayoutTouch() { return true; }

    @OnClick(R.id.cut_video_back_image_view)
    void onBackClick() {
        getActivity().onBackPressed();
    }

    @OnTouch(R.id.cut_video_thumbnail_scroll_view)
    boolean OnScrollTouch(MotionEvent motionEvent){
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//            mVideoOffsetMs
//                    = (int)((float)((mScrollView.getScrollX() * CUT_STANDARD_MILLISECOND) / mWindowWidth));
//            mVideoView.reStartAtMs(mVideoOffsetMs);
//            restartVideoAt( mVideoOffsetMs );
            mPresenter.scrollPositionChanged( mScrollView.getScrollX(), mWindowWidth );
        }
        return false;
    }
    @OnClick(R.id.cut_video_submit_button)
    void OnSubmitClick( View view ) {
        mPresenter.cutVideo( mVideoPath );
    }

    private Unbinder mUnbinder;
    String mVideoPath;
    int mWindowWidth;

    public static VideoCutFragment newInstance( String videoPath ) {
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

    @Override
    public void restartVideoAt( int ms ) {
        Log.d(TAG, "restartVideoAt: " + ms);
        mVideoView.seekTo( ms );
        if (!mVideoView.isPlaying()) mVideoView.start();
    }

    // real start time of this fragment
    @Override
    public void checkLayoutMeasured() {
//        mThumbnailContainerHeight = mThumbnailContainer.getMeasuredHeight();
        Point displaySize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        mWindowWidth = displaySize.x;

        mVideoView.setVideoPath(mVideoPath);
        mVideoView.start();

        mPresenter.updateSplitThumbnails(mVideoPath, mThumbnailContainer.getMeasuredHeight(), displaySize.x);
    }


    @Override
    public void updateThumbnails(Bitmap bitmap) {
//        mVideoView.pauseView();
        mVideoView.pause();
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
    public void providingRuntime() {
        mPresenter.receivingRunTime( mVideoView.getCurrentPosition() );
    }


    @Override
    public void enableProgress() {
        mDisableLayout.setVisibility(View.VISIBLE);
        Log.d(TAG, "enableProgress: ");
    }

    @Override
    public void disableProgress() {
        mDisableLayout.setVisibility(View.GONE);
        mPresenter.setRuntimeWatcher();
        Log.d(TAG, "disableProgress: ");
    }

    @Override
    public void nextActivity(String cutAudioPath) {
//        Intent intent = VideoEditActivity.newIntent(getContext(), cutAudioPath, null);
        String testAudioPath =
                "/storage/emulated/0/Download/sample_song_221s.mp3";
        Intent intent = VideoEditActivity.newIntent(getContext(), cutAudioPath, testAudioPath, 0);
        startActivity(intent);
    }

    @Override
    public void onError() {
        Log.d(TAG, "onError: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onResume();
    }

    int currentPosition = 0;
    @Override
    public void onStop() {
        currentPosition = mVideoView.getCurrentPosition();
        mVideoView.pause();
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVideoView.seekTo(currentPosition);
        mVideoView.start();
    }
}
