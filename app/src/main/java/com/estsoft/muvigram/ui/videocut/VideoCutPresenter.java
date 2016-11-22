package com.estsoft.muvigram.ui.videocut;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.estsoft.muvigram.data.MediaManager;
import com.estsoft.muvigram.data.local.FileNotSupportException;
import com.estsoft.muvigram.injection.PerSingleFragment;
import com.estsoft.muvigram.ui.base.BasePresenter;
import com.estsoft.muvigram.util.RxUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
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
    final int PROGRESS_MAX = 100 ;
    final int MICRO_MILLI_WEIGHT = 1000;
    final int MILLI_WEGHT = 1000;

    final MediaManager mMediaManager;
    Subscription mSubscription;
    Subscription mRuntimeWatcher;
    String mCutVideoPath;
    int mCurrentRunTime;
    boolean isGone;
    int mVideoOffsetMs;
    int mMediaPlayerOffsetMs;

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
    public void setMediaPlayerOffsetMs( int offsetMs) { mMediaPlayerOffsetMs = offsetMs; }
    public void setRuntimeWatcher(){
        RxUtil.unsubscribe(mRuntimeWatcher);
        RxUtil.unsubscribe(mSubscription);
        mRuntimeWatcher = getVideoRunTimeObserver()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        integer -> {
                            Log.d(TAG, "setRuntimeWatcher: " + integer + " / " + VIDEO_CUT_DURATION + " / " + mMediaPlayerOffsetMs);
                            if (integer >= VIDEO_CUT_DURATION + mMediaPlayerOffsetMs) {
                                getMvpView().restartVideoAt(mMediaPlayerOffsetMs);
                            }
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        },
                        () -> {
                            Log.e(TAG, "setRuntimeWatcher: " + " RuntimeWatcher is over ");
                        });
    }



    public void cutVideo( String videoPath ) {
        getMvpView().enableProgress( PROGRESS_MAX );
        RxUtil.unsubscribe(mSubscription);
        RxUtil.unsubscribe(mRuntimeWatcher);
        mCutVideoPath = mMediaManager.getTrimmingTargetVideoFilePath();
        Log.e(TAG, "cutVideo: " +  mMediaPlayerOffsetMs + " / " + (VIDEO_CUT_DURATION + mMediaPlayerOffsetMs) );
        mSubscription = mMediaManager.getVideoTrimmingProcess( videoPath, mMediaPlayerOffsetMs, (VIDEO_CUT_DURATION + mMediaPlayerOffsetMs) )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .distinct()
                .subscribe(
                        process -> {
                                getMvpView().updateProgress(process, getMvpView().TASK_TRANSCODE);
                        },
                        e -> {
                            checkError( e );
                        },
                        () -> {
                            getMvpView().disableProgress();
                            getMvpView().nextActivity( mCutVideoPath );
                        }
                );


//        mSubscription = mMediaManager.getMpegProcess( videoPath, mMediaPlayerOffsetMs, VIDEO_CUT_DURATION / 1000 )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.computation())
//                .subscribe(
//                        string -> {
//                            mCutVideoPath = string;
//                        },
//                        throwable -> {
//                            getMvpView().onError();
//                        },
//                        () -> {
//                            getMvpView().disableProgress();
//                            getMvpView().nextActivity(mCutVideoPath);
//                        }
//                );
    }
    private List<Long> getFrameList( int duration, int interval ) {
        List<Long> frameList = new ArrayList<>();
        for (int i = 0 ; true; i ++) {
            if ((i * interval) > duration ) {
                if ( (duration - (( i - 1 ) * interval)) > interval / 2 ) frameList.add( (long)(duration - 100) );
                break;
            }
            frameList.add( (long)(interval * i) );
        }
        return frameList;
    }

    public void updateSplitThumbnails( String videoPath, int thumbnailHeight, int windowWidth ) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        int videoDuration = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        int thumbnailIntervalMs = Math.round(VIDEO_CUT_DURATION / (((float)windowWidth / (float)thumbnailHeight)));
        List<Long> frameList = getFrameList( videoDuration, thumbnailIntervalMs );
        int thumbnailCount = frameList.size();

        Log.d(TAG, "updateSplitThumbnails: " + "h : " + thumbnailHeight + "        w : " + windowWidth + "\t d : " + videoDuration);

        getMvpView().enableProgress( thumbnailCount );
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        RxUtil.unsubscribe(mRuntimeWatcher);
        mSubscription = getThumbnailTaskOb(videoPath, frameList, thumbnailHeight )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Observer<Bitmap>() {
                            int taskCount = 0;
                               @Override
                               public void onCompleted() {
                                   getMvpView().restartVideoAt( mVideoOffsetMs );
                                   getMvpView().disableProgress();
                                   setRuntimeWatcher();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   getMvpView().onError( getMvpView().ERROR_ANONYMOUS );
                               }

                               @Override
                               public void onNext(Bitmap bitmap) {
                                    getMvpView().updateThumbnails(bitmap);
                                    getMvpView().updateProgress( (taskCount ++ * 100) / thumbnailCount, getMvpView().TASK_THUMBNAIL);
                               }
                           }
                );

    }

    // pure logic
    private Observable<Bitmap> getThumbnailTaskOb(String videoPath, List<Long> framesMs, int frameSize ) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        return Observable.create(
                subscriber -> {
                    for ( Long frame : framesMs ) {
                        if (isGone) break;
                        Bitmap bitmap = retriever.getFrameAtTime( frame * MICRO_MILLI_WEIGHT, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC );
                        bitmap = Bitmap.createScaledBitmap( bitmap, frameSize, frameSize, true );
                        subscriber.onNext( bitmap );
                    }
                    subscriber.onCompleted();
                }
        );
    }
//    private Observable<Bitmap> getThumbnailTaskOb(String videoPath, int interval, int height, int count ){
//
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        retriever.setDataSource(videoPath);
//        return Observable.create( subscriber -> {
//            for (int i = 0; i <= count; i ++) {
////                if ( isGone ) break;
//                Bitmap bitmap = retriever.getFrameAtTime(
//                        interval * MICRO_MILLI_WEIGHT * (i),
//                        MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
//                bitmap = Bitmap.createScaledBitmap(bitmap, height, height, true);
//                subscriber.onNext(bitmap);
//            }
//            subscriber.onCompleted();
//        });
//    }

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
                    subscriber.onError( e );
                }
            }
            subscriber.onCompleted();
        });
    }

    boolean isStop;
    public void onResume() {
        isStop = false;
        Log.d(TAG, "onStop: false ");
        setRuntimeWatcher();
    }
    public void onStop() {
        isStop = true;
        Log.d(TAG, "onStop: true ");
    }

    public void onDestroy() {
        isGone = true;
        RxUtil.unsubscribe(mSubscription);
        RxUtil.unsubscribe(mRuntimeWatcher);
    }

    private boolean checkError( Throwable e ) {
        if ( e instanceof FileNotSupportException ) {
            getMvpView().onError( getMvpView().ERROR_FILE_NOT_SUPPORT );
            return true;
        } else if ( e instanceof IOException ) {
            getMvpView().onError( getMvpView().ERROR_ANONYMOUS );
            return true;
        }
        return false;
    }

}
