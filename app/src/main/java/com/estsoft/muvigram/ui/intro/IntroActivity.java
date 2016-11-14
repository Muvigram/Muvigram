package com.estsoft.muvigram.ui.intro;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class IntroActivity extends BasePlainActivity implements IntroView {


    public static final int LOG_IN_ACTIVITY = 0;
    public static final int SIGN_UP_ACTIVITY = 1;
    public static final String KEY = "key";
    @BindView(R.id.intro_email_button) Button mEmailButton;
    @BindString(R.string.intro_buttin_email) String mEmailButtonText;
    @BindString(R.string.test_login) String mLoginText;
    @BindString(R.string.test_sign) String mSingupText;
    @BindView(R.id.intro_video_view) IncreasVideoView mVideoView;
    @Inject IntroPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlainActivityComponent().inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);


        mPresenter.attachView(this);
        mPresenter.checkViewAttached();
        mPresenter.loadVideo();


    }

    @OnClick(R.id.intro_twitter_button)
    public void skip() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @OnClick(R.id.intro_email_button)
    public void emailButtonClick() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setItems(new CharSequence[]{mLoginText, mSingupText}, (dialog1, which) -> {
//            Intent intent = new Intent(this, SignInActivity.class);
//            intent = (which == 0)
//                    ? intent.putExtra(KEY, LOG_IN_ACTIVITY) : intent.putExtra(KEY, SIGN_UP_ACTIVITY);
//            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        });
//        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void videoPlay(final MediaPlayer mediaPlayer) {
        if (mVideoView != null) {
            mediaPlayer.setVolume(0, 0);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /*****
     * MVP View methods implementation
     *****/

    @Override
    public void playIntroView(final Uri videoFile) {

        if (mVideoView != null && !mVideoView.isPlaying()) {
            mVideoView.setVideoURI(videoFile);
            mVideoView.setOnPreparedListener(mediaPlayer -> videoPlay(mediaPlayer));
            mVideoView.setOnCompletionListener(mediaPlayer -> videoPlay(mediaPlayer));
        }
    }
}
