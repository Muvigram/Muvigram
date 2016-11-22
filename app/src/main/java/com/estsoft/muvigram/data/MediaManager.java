package com.estsoft.muvigram.data;

import android.content.Context;
import android.util.Log;

import com.estsoft.muvigram.data.local.FFmpegSupporter;
import com.estsoft.muvigram.data.local.FileNotSupportException;
import com.estsoft.muvigram.data.local.MediaStorageService;
import com.estsoft.muvigram.injection.qualifier.ApplicationContext;
import com.estsoft.muvigram.model.VideoMetaData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by estsoft on 2016-11-03.
 */

public class MediaManager {
    private static final String TAG = "MediaManager";
    private final Context mContext;
    private final MediaStorageService mMediaStorageService;
    private final FFmpegSupporter mFFmpegSupporter;
    final String EDIT_DIR = "/edit";
    final File mEditingDirectory;
    final String mTrimmingTargetFilePath;

    @Inject
    public MediaManager(@ApplicationContext Context context, MediaStorageService mediaStorageService, FFmpegSupporter fFmpegSupporter) {
        mContext = context;
        mMediaStorageService = mediaStorageService;
        mFFmpegSupporter = fFmpegSupporter;

        mEditingDirectory = new File(mContext.getExternalFilesDir(null), EDIT_DIR);
        if (!mEditingDirectory.exists()) mEditingDirectory.mkdirs();
        mTrimmingTargetFilePath = mEditingDirectory.getAbsolutePath() + File.separator + "trim_result.mp4";
        Log.d(TAG, "MediaManager: " + mTrimmingTargetFilePath);
    }

    /* API Data supplier - Business logic*/
    public Observable<VideoMetaData> getVideoMetaData() {
        return mMediaStorageService.getVideoThumbnailDurationMetaData();
    }

    public Observable<String[]> getAlbumMetaData( String audioPath ) {
        return mMediaStorageService.getAudioMetaData( audioPath );
    }

//    public Observable<String> getMpegProcess(String videoPath, int startTimeMs, int runTimeSeconds) {
//        return mFFmpegSupporter.cutVideo( videoPath, startTimeMs, runTimeSeconds);
//    }


    public String getTrimmingTargetFilePath() {
        return mTrimmingTargetFilePath;
    }
    public Observable< ? extends Integer > getTrimmingProcess(String videoPath, int startTimeMs, int runTimeMs) {
        Observable< ? extends Integer > observable;
        try {
            observable = mFFmpegSupporter.cutVideoM4M(videoPath, startTimeMs, runTimeMs, mTrimmingTargetFilePath);
        } catch ( Exception e ) {
            observable = PublishSubject.create();
            ((PublishSubject)observable).onError( e );
        }
        return  observable;
    }

    private Observable< Object > getErrorObservable( Exception exception ) {
        return Observable.from( new Exception[] { exception } );
    }

}