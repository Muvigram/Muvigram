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

    final MediaManager mMediaManager;
    Subscription mSubscription;
    String mCutVideoPath;

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
    public void cutVideo( String videoPath, int startTimeMs, int runTimeSeconds ) {
        getMvpView().enableProgress();

        Log.d(TAG, "cutVideo: ");
        mSubscription = mMediaManager.getMpegProcess( videoPath, startTimeMs, runTimeSeconds )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(
                        string -> {
                            mCutVideoPath = string;
                            Log.d(TAG, "cutVideo Process ... : " + string);
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

    public void updateSplitThumbnails( String videoPath, int videoDuration, int interval, int height ) {
        getMvpView().enableProgress();
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = getThumbnailTaskOb(videoPath, videoDuration, interval, height)
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
                            getMvpView().disableProgress();
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
                Bitmap bitmap = retriever.getFrameAtTime(
                        interval * getMvpView().MILLI_WEIGHT * (i),
                        MediaMetadataRetriever.OPTION_PREVIOUS_SYNC);
                bitmap = Bitmap.createScaledBitmap(bitmap, height, height, true);
                subscriber.onNext(bitmap);
            }
            subscriber.onCompleted();
        });
    }
}
