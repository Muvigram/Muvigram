package com.estsoft.muvigram.ui.intro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.ui.base.BaseActivity;
import com.estsoft.muvigram.ui.login.LoginActivity;
import com.estsoft.muvigram.ui.sign.SignInActivity;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class IntroActivity extends BaseActivity implements IntroView {

    @BindString(R.string.test_login) String loginText;
    @BindString(R.string.test_sign) String singupText;
    @BindView(R.id.intro_video_view) IncreasVideoView mVideoView;
    @Inject IntroPresenter mIntroPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        mIntroPresenter.attachView(this);
        mIntroPresenter.checkViewAttached();
        mIntroPresenter.loadVideo();
    }

    @OnClick(R.id.intro_email_button)
    public void emailButtonClick() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setItems( new CharSequence[]{loginText, singupText}, (dialog1, which) -> {



            Intent intent = which == 1 ? new Intent(this, LoginActivity.class) : new Intent(this, SignInActivity.class);
            startActivity(intent);

            
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIntroPresenter.detachView();
    }

    private void videoPlay(final MediaPlayer mediaPlayer) {
        if(mVideoView != null) {
            mediaPlayer.setVolume(0, 0);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void playIntroView(final Uri videoFile) {

        if(mVideoView != null && !mVideoView.isPlaying())
        {
            mVideoView.setVideoURI(videoFile);
            mVideoView.setOnPreparedListener( mediaPlayer -> videoPlay(mediaPlayer));
            mVideoView.setOnCompletionListener( mediaPlayer -> videoPlay(mediaPlayer));
        }
    }
}
