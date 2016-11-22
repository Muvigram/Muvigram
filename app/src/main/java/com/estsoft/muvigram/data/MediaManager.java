package com.estsoft.muvigram.data;

import android.content.Context;
import android.util.Log;

import com.estsoft.muvigram.data.local.FFmpegSupporter;
import com.estsoft.muvigram.data.local.MediaStorageService;
import com.estsoft.muvigram.injection.qualifier.ApplicationContext;
import com.estsoft.muvigram.model.VideoMetaData;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

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
    final String mTrimmingTargetVideoFilePath;
    final String mTrimmingTargetAudioFilePath;
    final String mCreatedTargetFilePath;

    @Inject
    public MediaManager(@ApplicationContext Context context, MediaStorageService mediaStorageService, FFmpegSupporter fFmpegSupporter) {
        mContext = context;
        mMediaStorageService = mediaStorageService;
        mFFmpegSupporter = fFmpegSupporter;

        mEditingDirectory = new File(mContext.getExternalFilesDir(null), EDIT_DIR);
        if (!mEditingDirectory.exists()) mEditingDirectory.mkdirs();
        mTrimmingTargetVideoFilePath = mEditingDirectory.getAbsolutePath() + File.separator + "trim_video.mp4";
        mTrimmingTargetAudioFilePath = mEditingDirectory.getAbsolutePath() + File.separator + "trim_audio.mp3";
        mCreatedTargetFilePath = mEditingDirectory.getAbsolutePath() + File.separator + "result.mp4";
    }

    /* API Data supplier - Business logic*/
    public Observable<VideoMetaData> getVideoMetaData() {
        return mMediaStorageService.getVideoThumbnailDurationMetaData();
    }

    public Observable<String[]> getAlbumMetaData( String audioPath ) {
        return mMediaStorageService.getAudioMetaData( audioPath );
    }

    public Observable< ? extends Integer > getVideoTrimmingProcess(String videoPath, int startTimeMs, int runTimeMs) {
        Observable< ? extends Integer > observable;
        try {
            observable = mFFmpegSupporter.cutVideoM4M(videoPath, startTimeMs, runTimeMs, mTrimmingTargetVideoFilePath);
        } catch ( Exception e ) {
            observable = getErrorSubject( e );
        }
        return  observable;
    }

    public Observable< ? extends Integer > getAudioTrimmingProcess( String audioPath, int audioOffset, int runtimeBuffer ) {
        Observable< ? extends Integer > observable;
        try {
            observable = mFFmpegSupporter.cutAudio(audioPath, audioOffset, runtimeBuffer, mTrimmingTargetAudioFilePath);
        } catch ( FFmpegCommandAlreadyRunningException e ){
            observable = getErrorSubject( e );
        }
        return observable;
    }

    public Observable< ? extends Integer > getVideoCreatingProcess( String videoPath, String audioPath, int audioOffset ) {
        return null;
    }

    private PublishSubject getErrorSubject( Exception exception ) {
        PublishSubject subject = PublishSubject.create();
        subject.onError( exception );
        return subject;
    }

    public String getTrimmingTargetVideoFilePath() {
        return mTrimmingTargetVideoFilePath;
    }
    public String getTrimmingTargetAudioFilePath() { return mTrimmingTargetAudioFilePath; }
    public String getCreatedTargetFilePath() { return  mCreatedTargetFilePath; }


//    private Observable< Object > getErrorObservable( Exception exception ) {
//        return Observable.from( new Exception[] { exception } );
//    }

//    public Observable<String> getMpegProcess(String videoPath, int startTimeMs, int runTimeSeconds) {
//        return mFFmpegSupporter.cutVideo( videoPath, startTimeMs, runTimeSeconds);
//    }

}