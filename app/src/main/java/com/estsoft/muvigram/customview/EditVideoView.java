package com.estsoft.muvigram.customview;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.VideoView;

/**
 * Created by estsoft on 2016-10-28.
 */

public class EditVideoView extends VideoView implements MediaPlayer.OnPreparedListener {

    private static final String TAG = "EditVideoView";
    private final int SLEEP_INTERVAL_MS = 100;
    private final int PLAY_STOP_BUFFER = 200;

    private MediaPlayer mMediaPlayer;

    private int playDurationMs;
    private int totalDurationMs;
    private int stopPlayPositionMs;
    private int reStartPositionMs;
    private int pauseStopPositionMs;

    private Thread runtimeWatcher;
    volatile boolean runtimeWatcherPaused = false;
    private OnRestartListener mRestartListener;

    public void init(int playDurationMs, int totalDurationMs ) {
        this.totalDurationMs = totalDurationMs;
        this.playDurationMs = playDurationMs;
        runtimeWatcherPaused = true;
    }

    public void reStartAtMs( int ms ) {

        reStartPositionMs = ms;
        if (!runtimeWatcherPaused) {
            stopPlayPositionMs = reStartPositionMs + playDurationMs;
        } else {
            runtimeWatcherPaused = false;
            runtimeWatcher = geRuntimeWatcher();
            runtimeWatcher.start();
        }
        this.seekTo(reStartPositionMs);
        if (!this.isPlaying()) this.start();

        if (mRestartListener != null) mRestartListener.OnRestart();
    }

    private Thread geRuntimeWatcher() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                int currentPlayingPosition;
                while( !runtimeWatcherPaused ) {
                    currentPlayingPosition = getCurrentPosition();
                    try {
                        Thread.sleep(SLEEP_INTERVAL_MS);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: Thread interrupted ");
                        e.printStackTrace();
                    }
                    if ( currentPlayingPosition >= stopPlayPositionMs
                            || currentPlayingPosition + PLAY_STOP_BUFFER >= totalDurationMs) {
                        reStartAtMs(reStartPositionMs);
                    }
                }
            }
        });
    }

    public void stopView() {
        runtimeWatcherPaused = true;
        runtimeWatcher = null;
    }

    public void pauseView() {
        if (isPlaying()) pause();
        pauseStopPositionMs = getCurrentPosition();
        runtimeWatcherPaused = true;
        runtimeWatcher = null;
    }

    public void resumeView() {
        reStartAtMs(pauseStopPositionMs);
    }

    public void mute() {
        this.setVolume(0);
    }

    public void unmute() {
        this.setVolume(100);
    }

    private void setVolume(int amount) {
        final int max = 100;
        final double numerator = max - amount > 0 ? Math.log(max - amount) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));

        this.mMediaPlayer.setVolume(volume, volume);
        Log.d(TAG, "setVolume: muting " +volume);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public int getStopPlayPositionMs() {
        return stopPlayPositionMs;
    }

    public EditVideoView(Context context) {
        super(context);
    }

    public EditVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnPreparedListener( this );
    }

    public EditVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    public void setOnRestartListener(OnRestartListener mRestartListener) {
        this.mRestartListener = mRestartListener;
    }

    public interface OnRestartListener {
        void OnRestart();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        final int deviceWidth = displayMetrics.widthPixels;
        final int deviceHeight = displayMetrics.heightPixels;
        setMeasuredDimension(deviceWidth, deviceHeight);
    }


    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

}
