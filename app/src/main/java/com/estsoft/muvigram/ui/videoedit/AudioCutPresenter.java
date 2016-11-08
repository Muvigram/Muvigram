package com.estsoft.muvigram.ui.videoedit;

import android.util.Log;

import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by estsoft on 2016-11-05.
 */

@PerSingleFragment
public class AudioCutPresenter extends BasePresenter<AudioCutView> {
    private static final String TAG = "AudioCutPresenter";

    final int TOUCH_SENSITIVITY = 25;
    float mTouchOldX;
    float mTouchNewX;
    float mAudioOffsetPixel;

    int mAudioCurrentPositionMs;
    int mAudioOffsetMs;
    boolean isStopped;

    Subscription mRuntimeWatcher;

    @Inject
    public AudioCutPresenter(  ) {

    }

    @Override
    public void attachView(AudioCutView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    //logic here
    public void videoRestarted() {
        getMvpView().restartVideoAt( 0 );
        getMvpView().restartAudioAt( mAudioOffsetMs );
    }

    public void setAudioOffset( int audioOffset, int audioOffsetPixel ) {
        mAudioOffsetMs = audioOffset;
        mAudioOffsetPixel = audioOffsetPixel;
    }

    public void setmTouchOldX( float x ) {
        mTouchOldX = x;
    }

    public void waveformTouchMoved( float x, int audioDurationPixel, int windowWidth ) {
        mTouchNewX = x;
        if (Math.abs(mTouchOldX - mTouchNewX) > TOUCH_SENSITIVITY ) {
            mAudioOffsetPixel = mAudioOffsetPixel + (mTouchOldX - mTouchNewX);
            if (mAudioOffsetPixel < 0) mAudioOffsetPixel = 0;
            else if (mAudioOffsetPixel > audioDurationPixel - windowWidth)
                mAudioOffsetPixel = audioDurationPixel - windowWidth;
            mTouchOldX = mTouchNewX;
            //set mAudioOffsetMs...
            requestMediaPixelToMilliSecond( (int)mAudioOffsetPixel );
            getMvpView().updateDisplay( mAudioCurrentPositionMs, mAudioOffsetMs );
        }
    }

    public void waveformTouchEnd( ) {
        getMvpView().restartVideoAt( 0 );
        getMvpView().restartAudioAt( mAudioOffsetMs );
    }

    public void requestMediaPixelToMilliSecond( int pixel ) { getMvpView().providingPixelToMillis( pixel ); }
    public void receiveMediaPixelToMilliSecond ( int ms ) { mAudioOffsetMs = ms; }
    public void requestMediaCurrentPosition(){
        getMvpView().providingMediaCurrentPosition();
    }
    public void receiveMediaCurrentPosition( int audioPosition ){ mAudioCurrentPositionMs = audioPosition; }
    public void startRuntimeWatcher() {
        isStopped = false;
        mRuntimeWatcher = getTimeStampOb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        audioPositionMs -> {
                            getMvpView().updateDisplay( audioPositionMs, mAudioOffsetMs );
                });
    }

    private Observable<Integer> getTimeStampOb () {
        return Observable.create(
                subscriber -> {
                    while( !isStopped ) {
                        requestMediaCurrentPosition();
                        subscriber.onNext(mAudioCurrentPositionMs);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e ) {
                            e.printStackTrace();
                        }
                    }
                    subscriber.onCompleted();
        });
    }

    public void onStop() {
        isStopped = true;
    }
}
