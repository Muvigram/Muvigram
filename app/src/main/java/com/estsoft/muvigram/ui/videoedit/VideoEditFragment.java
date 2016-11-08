package com.estsoft.muvigram.ui.videoedit;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.customview.MusicRecordView;
import com.estsoft.muvigram.customview.StreamTextView;
import com.estsoft.muvigram.injection.qualifier.ParentFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoEditFragment extends BaseSingleFragment implements VideoEditView {

    private static final String TAG = "VideoEditFragment";
    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoEditFragment.video_path";
    private static final String ARG_AUDIO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoEditFragment.audio_path";
    private static final String ARG_AUDIO_OFFSET
            = "com.estsoft.muvigram.ui.videoedit.VideoEditFragment.audio_offset";

    @Inject VideoEditPresenter mPresenter;
    @Inject MediaPlayer mAudioPlayer;
    @Inject @ParentFragment FragmentManager mFragmentManager;

    @BindView(R.id.edit_video_title_text_view)
    TextView mTitle;
    @BindView(R.id.edit_video_video_view)
    IncreasVideoView mVideoView;
    @BindView(R.id.edit_video_back_image_view)
    ImageView mbackButton;
    @BindView(R.id.edit_video_audio_edit_button)
    ImageView mAudioEditButton;
    @BindView(R.id.edit_video_audio_album)
    MusicRecordView mAlbumView;

    @OnClick(R.id.edit_video_back_image_view)
    void OnBackClick() { getActivity().onBackPressed(); }

//    MediaPlayer mAudioPlayer;

    String mCutVideoPath;
    String mAudioPath;
    int mVideoDuration;
    int mAudioStatus = NO_AUDIO;
    int mAudioOffset;

    private Unbinder mUnbinder;

    public static VideoEditFragment newInstance(String videoPath, String audioPath, int audioOffset) {
        Bundle args = new Bundle();
        args.putString( ARG_VIDEO_PATH, videoPath );
        args.putString( ARG_AUDIO_PATH, audioPath );
        args.putInt( ARG_AUDIO_OFFSET, audioOffset);
        VideoEditFragment videoEditFragment = new VideoEditFragment();
        videoEditFragment.setArguments(args);
        return videoEditFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCutVideoPath = getArguments().getString(ARG_VIDEO_PATH);
        mAudioPath = getArguments().getString(ARG_AUDIO_PATH);
        mAudioOffset = getArguments().getInt( ARG_AUDIO_OFFSET );
        if ( mAudioPath == null ) {
            mAudioStatus = NO_AUDIO;
            Log.e(TAG, "onCreate: cut video path ... " + mCutVideoPath + "  audio status ... no audio" );
        } else {
            mAudioStatus = WITH_AUDIO;
            Log.e(TAG, "onCreate: cut video path ... " + mCutVideoPath + "  audio status ... " + mAudioPath );
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_edit, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mVideoView.setOnPreparedListener( mediaPlayer ->   {          mVideoView.start(); mAudioPlayer.start();         });
        mVideoView.setOnCompletionListener( mediaPlayer ->  mPresenter.videoRestarted()   );
        mVideoView.setVideoPath( mCutVideoPath );
        mVideoView.setZOrderMediaOverlay( false );
        mAlbumView.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> mAlbumView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate))
        );

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
        mPresenter.attachView(this);

        mPresenter.loadAudioMetaData(mAudioPath);

        if (mAudioStatus != NO_AUDIO) {
            try {
//                mAudioPlayer.setOnSeekCompleteListener( mp -> mp.start() );
                mAudioPlayer.setDataSource(mAudioPath);
                mAudioPlayer.prepare();
            } catch ( IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* View logic here ... */
    @Override
    public void onStart() {
        super.onStart();

        //video prepare
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(mCutVideoPath);

        mAudioPlayer.seekTo(mAudioOffset);
//        mVideoView.seekTo( 0 );
        // for audioCutFragments
        mVideoDuration = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

        mPresenter.setAudioOffset(mAudioOffset);
    }

    @Override
    public void reStartVideoView(int ms) {
        mVideoView.seekTo( ms );
        if(!mVideoView.isPlaying()) mVideoView.start();
    }

    @Override
    public void reStartAudio(int ms) {
        mAudioPlayer.seekTo( ms );
        if(!mAudioPlayer.isPlaying()) mAudioPlayer.start();
    }

    @Override
    public void updateAudioTitle(String title ){
        mTitle.setText( title );
    }

    @Override
    public void updateAudioAlbum(String path) {
        Log.d(TAG, "updateAudioAlbum: Album Updating! " + path);
        Picasso.with(getContext())
                .load( new File(path) )
                .transform(new CircleTransform())
                .into(mAlbumView);
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        if (mAudioPlayer != null) mAudioPlayer.pause();
        if (mVideoView != null) mVideoView.stopPlayback();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mAudioPlayer.stop();
        mAudioPlayer.reset();
        mVideoView.stopPlayback();
        super.onDestroyView();
    }

    @OnClick(R.id.edit_video_audio_edit_button)
    @Override
    public void nextAudioEditFragment() {
        mFragmentManager.beginTransaction().remove(this).commit();
        Fragment fragment =  AudioCutFragment.newInstance( mCutVideoPath, mAudioPath, mAudioOffset, mVideoDuration );
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    @OnClick(R.id.edit_video_audio_setting_button)
    @Override
    public void nextAudioSettingFragment() {
        Toast.makeText(getActivity(), "오디오 세팅", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.edit_video_video_filter_button)
    @Override
    public void nextVideoFilterFragment() {
        Toast.makeText(getActivity(), "비디오 필터", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.edit_video_video_speed_button)
    @Override
    public void nextVideoSpeedFragment() {
        Toast.makeText(getActivity(), "비디오 속도", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.edit_video_audio_album)
    @Override
    public void nextAudioSelectActivity() {
        Toast.makeText(getActivity(), "오디오 선택", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void nextActivity() {

    }



}
