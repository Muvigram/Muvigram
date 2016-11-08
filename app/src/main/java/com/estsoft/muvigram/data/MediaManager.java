package com.estsoft.muvigram.data;

import android.util.Log;

import com.estsoft.muvigram.data.local.FFmpegSupporter;
import com.estsoft.muvigram.data.local.MediaStorageService;
import com.estsoft.muvigram.model.VideoMetaData;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by estsoft on 2016-11-03.
 */

@Singleton
public class MediaManager {
    private static final String TAG = "MediaManager";
    private final MediaStorageService mMediaStorageService;
    private final FFmpegSupporter mFFmpegSupporter;

    @Inject
    public MediaManager(MediaStorageService mediaStorageService, FFmpegSupporter fFmpegSupporter) {
        mMediaStorageService = mediaStorageService;
        mFFmpegSupporter = fFmpegSupporter;
    }

    /* API Data supplier - Business logic*/
    public Observable<VideoMetaData> getVideoMetaData() {
        return mMediaStorageService.getVideoThumbnailDurationMetaData();
    }

    public Observable<String> getMpegProcess(String videoPath, int startTimeMs, int runTimeSeconds) {
        return mFFmpegSupporter.cutVideo( videoPath, startTimeMs, runTimeSeconds);
    }

    public Observable<String[]> getAlbumMetaData( String audioPath ) {
        return mMediaStorageService.getAudioMetaData( audioPath );
    }


}