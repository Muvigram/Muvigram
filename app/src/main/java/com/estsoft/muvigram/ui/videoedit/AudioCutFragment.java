package com.estsoft.muvigram.ui.videoedit;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.customview.WaveformView;
import com.estsoft.muvigram.injection.qualifier.ParentFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.util.sounds.CheapSoundFile;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by estsoft on 2016-11-04.
 */

public class AudioCutFragment extends BaseSingleFragment implements AudioCutView {

    private static final String TAG = "AudioCutFragment";

    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.AudioCutFragment.video_path";
    private static final String ARG_AUDIO_PATH
            = "com.estsoft.muvigram.ui.videoedit.AudioCutFragment.audio_path";
    private static final String ARG_VIDEO_DURATION
            = "com.estsoft.muvigram.ui.videoedit.AudioCutFragment.video_duration";
    private static final String ARG_AUDIO_OFFSET
            = "com.estsoft.muvigram.ui.videoedit.AudioCutFragment.audio_offset";

    @Inject
    AudioCutPresenter mPresenter;
    @Inject
    MediaPlayer mAudioPlayer;
    @Inject @ParentFragment
    FragmentManager mFragmentManager;

    @BindView(R.id.audio_cut_video_view)
    IncreasVideoView mVideoView;
    @BindView(R.id.audio_cut_waveform_container)
    LinearLayout mWaveformContainer;
    @BindView(R.id.audio_cut_submit)
    ImageView mSubmitButton;
    @BindView(R.id.audio_cut_waveform_view)
    WaveformView mWaveformView;

    @OnClick(R.id.audio_cut_submit)
    public void OnsubmitClick( View view ) {
        backToVideoEditFragment();
    }

    @OnTouch(R.id.audio_cut_waveform_view)
    public boolean OnWaveformTouch (MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN :
                mPresenter.setmTouchOldX( motionEvent.getX() );
                break;
            case MotionEvent.ACTION_MOVE :
                mPresenter.waveformTouchMoved( motionEvent.getX(), mAudioDurationPixel, mWindowWidth );
                break;
            case MotionEvent.ACTION_UP :
                mPresenter.waveformTouchEnd();
            default :
                break;
        }
        return true;
    }

//    MediaPlayer mAudioPlayer;

    String mVideoPath;
    String mAudioPath;
    int mAudioOffset;
    int mVideoDuration;

    int mWindowWidth;
    int mAudioDurationPixel;

    private Unbinder mUnbinder;

    public static AudioCutFragment newInstance( String videoPath, String audioPath, int audioOffset, int videoDuration ) {
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_PATH, videoPath);
        args.putString(ARG_AUDIO_PATH, audioPath);
        args.putInt(ARG_AUDIO_OFFSET, audioOffset);
        args.putInt(ARG_VIDEO_DURATION, videoDuration);
        AudioCutFragment audioCutFragment = new AudioCutFragment();
        audioCutFragment.setArguments(args);
        return audioCutFragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        mVideoPath = getArguments().getString(ARG_VIDEO_PATH);
        mAudioOffset = getArguments().getInt( ARG_AUDIO_OFFSET );
        mAudioPath = getArguments().getString(ARG_AUDIO_PATH);
        mVideoDuration = getArguments().getInt(ARG_VIDEO_DURATION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_audio_cut, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mVideoView.setOnPreparedListener(mediaPlayer ->     {        mVideoView.start(); mAudioPlayer.start(); }       );
        mVideoView.setOnCompletionListener( mediaPlayer ->  mPresenter.videoRestarted()   );

        mVideoView.setVideoPath(mVideoPath);
        mVideoView.setZOrderMediaOverlay(false);

        mWaveformView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWaveformView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                checkLayoutMeasured();
                initWaveformView();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
        mPresenter.attachView(this);

        try {
//            mAudioPlayer.setOnSeekCompleteListener( mp -> mp.start() );
            mAudioPlayer.setDataSource(mAudioPath);
            mAudioPlayer.prepare();
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }

    /* View logic here ... */

    @Override
    public void onStart() {
        super.onStart();
        mAudioPlayer.seekTo(mAudioOffset);
//        mVideoView.seekTo( 0 );
        mPresenter.startRuntimeWatcher();
    }

    @Override
    public void updateDisplay(int audioPositionMs, int audioOffsetMs) {
        mWaveformView.setParameters( audioPositionMs,  mWaveformView.millisecondToPixel(audioOffsetMs) );
        mWaveformView.invalidate();
    }

    @Override
    public void restartVideoAt(int ms) {
        mVideoView.seekTo( ms );
        if(!mVideoView.isPlaying()) mVideoView.start();
    }

    @Override
    public void restartAudioAt(int ms) {
        mAudioOffset = ms;
        mAudioPlayer.seekTo( ms );
        if(!mAudioPlayer.isPlaying()) mAudioPlayer.start();
    }

    @Override
    public void providingMediaCurrentPosition() {
          mPresenter.receiveMediaCurrentPosition(  mAudioPlayer.getCurrentPosition()  );
    }
    @Override
    public void providingPixelToMillis( int pixel ) {
        mPresenter.receiveMediaPixelToMilliSecond( mWaveformView.pixelToMillisecond( pixel ) );
    }

    @Override
    public void backToVideoEditFragment() {
        mFragmentManager.beginTransaction().remove(this).commit();
        Fragment fragment =  VideoEditFragment.newInstance( mVideoPath, mAudioPath, mAudioOffset );
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    public void checkLayoutMeasured() {
        Point displaySize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
        mWindowWidth = displaySize.x;
    }

    public void initWaveformView(){
        try {
            CheapSoundFile soundFile = CheapSoundFile.create(mAudioPath, null);
            mWaveformView.init(soundFile, mVideoDuration);
            mAudioDurationPixel = mWaveformView.millisecondToPixel(mAudioPlayer.getDuration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPresenter.setAudioOffset( mAudioOffset, mWaveformView.millisecondToPixel( mAudioOffset ) );
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAudioPlayer != null) mAudioPlayer.pause();
        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        mAudioPlayer.stop();
        mAudioPlayer.reset();
        mVideoView.stopPlayback();
        super.onDestroyView();
    }
}
