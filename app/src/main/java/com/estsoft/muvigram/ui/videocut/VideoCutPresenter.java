package com.estsoft.muvigram.ui.videocut;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragment
public class VideoCutPresenter extends BasePresenter<VideoCutView> {
    private static final String TAG = "VideoCutPresenter";

    final int VIDEO_CUT_DURATION = 15000;

    final MediaManager mMediaManager;
    Subscription mSubscription;
    Subscription mRuntimeWatcher;
    String mCutVideoPath;
    int mCurrentRunTime;
    boolean isGone;
    int mVideoOffsetMs;

    @Inject
    public VideoCutPresenter( MediaManager mediaManager ) {
        mMediaManager = mediaManager;
    }

    @Override
    public void attachView(VideoCutView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    /* Business logic here ... */

    public void requestVideoRuntime() {
        getMvpView().providingRuntime();
    }
    public void receivingRunTime(int runtime) {
        mCurrentRunTime = runtime;
    }
    public void scrollPositionChanged(int scrollX, int windowWidth ) {
        mVideoOffsetMs = (int)((float)((scrollX * VIDEO_CUT_DURATION) / windowWidth));
        getMvpView().restartVideoAt( mVideoOffsetMs );
    }
    public void setRuntimeWatcher(){
        RxUtil.unsubscribe(mRuntimeWatcher);
        RxUtil.unsubscribe(mSubscription);
        mRuntimeWatcher = getVideoRunTimeObserver()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        integer -> {
                            Log.d(TAG, "setRuntimeWatcher: " + integer + " / " + VIDEO_CUT_DURATION + " / " + mVideoOffsetMs);
                            if (integer >= VIDEO_CUT_DURATION + mVideoOffsetMs) {
                                getMvpView().restartVideoAt(mVideoOffsetMs);
                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        },
                        () -> {
                        });
    }

    public void cutVideo( String videoPath ) {
        getMvpView().enableProgress();
        RxUtil.unsubscribe(mSubscription);
        RxUtil.unsubscribe(mRuntimeWatcher);
        mSubscription = mMediaManager.getMpegProcess( videoPath, mVideoOffsetMs, VIDEO_CUT_DURATION / 1000 )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                        string -> {
                            mCutVideoPath = string;
                        },
                        throwable -> {
                            getMvpView().onError();
                        },
                        () -> {
                            getMvpView().disableProgress();
                            getMvpView().nextActivity(mCutVideoPath);
                        }
                );
    }
    public void updateSplitThumbnails( String videoPath, int thumbnailHeight, int windowWidth ) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        int videoDuration = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        int thumbnailInterval = Math.round(VIDEO_CUT_DURATION / (((float)windowWidth / (float)thumbnailHeight)));

        getMvpView().enableProgress();
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        RxUtil.unsubscribe(mRuntimeWatcher);
        mSubscription = getThumbnailTaskOb(videoPath, videoDuration, thumbnailInterval, thumbnailHeight)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                        bitmap -> {
                            getMvpView().updateThumbnails(bitmap);
                        },
                        throwable -> {
                            getMvpView().onError();
                        },
                        () -> {
                            getMvpView().restartVideoAt( mVideoOffsetMs );
                            getMvpView().disableProgress();
                            setRuntimeWatcher();
                        }
                );

    }

    // pure logic
    private Observable<Bitmap> getThumbnailTaskOb(String videoPath, int videoDuration, int interval, int height ){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        return Observable.create( subscriber -> {
            int loopCount = videoDuration / interval;
            if (loopCount == 0 ) loopCount ++;
            for (int i = 0; i <loopCount; i ++) {
                if ( isGone ) break;
                Bitmap bitmap = retriever.getFrameAtTime(
                        interval * getMvpView().MILLI_WEIGHT * (i),
                        MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
                bitmap = Bitmap.createScaledBitmap(bitmap, height, height, true);
                subscriber.onNext(bitmap);
            }
            subscriber.onCompleted();
        });
    }

    private Observable<Integer> getVideoRunTimeObserver() {
        return Observable.create(subscriber -> {
            while( !isStop ) {
                requestVideoRuntime();
                subscriber.onNext( mCurrentRunTime );
                Log.e(TAG, "getVideoRunTimeObserver: Thread is alive!!" );
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e ) {
                    e.printStackTrace();
                }
            }
            subscriber.onCompleted();
        });
    }

    boolean isStop;
    public synchronized void onResume() {
        isStop = false;
        setRuntimeWatcher();
    }
    public synchronized void onStop() {
        isStop = true;

    }

    public void onDestroy() {
        isGone = true;
        RxUtil.unsubscribe(mSubscription);
        RxUtil.unsubscribe(mRuntimeWatcher);
    }
}
