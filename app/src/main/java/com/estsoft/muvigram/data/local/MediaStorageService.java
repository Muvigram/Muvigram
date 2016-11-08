package com.estsoft.muvigram.data.local;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.estsoft.muvigram.injection.qualifier.ApplicationContext;
import com.estsoft.muvigram.model.VideoMetaData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by estsoft on 2016-11-03.
 */

@Singleton
public class MediaStorageService {
    private static final String TAG = "MediaStorageService";

    private final int TYPE_VIDEO = 1;
    private final int TYPE_AUDIO = 2;
    private final int MILLI_TO_SECOND = 1000;
    private final String DCIM_CAMERA_PATH;
    private final String APP_FILES_DIR_PATH;

    private final Context mContext;

    public final String[] confirmedVideoExtensions = {
            "mp4", "3gp"
    };
    public final String[] confirmedAudioExtensions = {
            "mp3"
    };
    public final String[] audioMetaColumns = {
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID,
    };
    public final String[] audioAlbumColumns = {
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ART
    };

    @Inject
    public MediaStorageService(@ApplicationContext Context context) {
        mContext = context;
        DCIM_CAMERA_PATH = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM ).getAbsolutePath() + "/Camera";
        APP_FILES_DIR_PATH = mContext.getExternalFilesDir(null).getAbsolutePath();
    }

    /* API - Device - Data supplier */
    public Observable<VideoMetaData> getVideoThumbnailDurationMetaData() {
        List<String> videoPaths = getConfirmedVideoPathsFromCamera();
        String[] projection = new String[] {
                MediaStore.MediaColumns._ID,
                MediaStore.Video.VideoColumns.DURATION };
        String selection = MediaStore.MediaColumns.DATA + "=?";

        return Observable.create( subscriber -> {
                for ( String videoPath : videoPaths ) {
                    int imageId = -1, duration = -1;
                    String[] selectionArgs = new String[] { videoPath };
                    Cursor cursor = mContext.getContentResolver().query(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            projection,
                            selection,
                            selectionArgs,
                            null);
                    if (cursor != null && cursor.moveToFirst()) {
                        imageId = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
                        duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
                    }
                    cursor.close();

                    Bitmap bmThumbnail = MediaStore.Video.Thumbnails.getThumbnail(
                            mContext.getContentResolver(),
                            imageId,
                            MediaStore.Images.Thumbnails.MINI_KIND,
                            null
                    );

                    VideoMetaData metaData = new VideoMetaData(bmThumbnail, duration / MILLI_TO_SECOND, videoPath);
                    subscriber.onNext(metaData);
                }
                subscriber.onCompleted();
            });
    }

    public Observable<String[]> getAudioMetaData( String path ) {
        List<String> results = new ArrayList<>();
        return Observable.create( subscriber -> {
            Cursor cursor = mContext.getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    audioMetaColumns,
                    MediaStore.Audio.Media.DATA + " = ?",
                    new String [] {
                            path
                    },
                    "");
            if (cursor.moveToFirst()) {
                for ( String column : audioMetaColumns ) {
                    if (column.equals( MediaStore.Audio.Media.ALBUM_ID )) {
                        results.add( getAlbumPath( cursor.getString(cursor.getColumnIndex( column ) )));
                    } else {
                        results.add(cursor.getString(cursor.getColumnIndex(column)));
                    }
                }
                subscriber.onNext( results.toArray( new String[results.size()] ) );
            } else {
                subscriber.onError( new IOException() );
            }
            subscriber.onCompleted();
        });
    }

    // inner Method
    private String getAlbumPath( String id ) {
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                audioAlbumColumns,
                MediaStore.Audio.Albums._ID + " = ?",
                new String [] {
                        id
                },
                "");
        if (cursor.moveToFirst()) {
            return cursor.getString( cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART) );
        } else {
            return "";
        }
    }
    private List<String> getConfirmedVideoPathsFromCamera() {
        return scanFilesAt( DCIM_CAMERA_PATH, TYPE_VIDEO );
    }
    private List<String> scanFilesAt ( String dirPath, int fileType ) {
        String[] fileList = new File( dirPath ).list();
        List<String> confirmedFilePaths = new ArrayList<>();
        for ( String fileName : fileList ) {
            if ( checkExtension(fileName, fileType) )
                confirmedFilePaths.add(dirPath + "/" + fileName);
        }
        return confirmedFilePaths;
    }
    private boolean checkExtension(String fileName, int fileType ) {
        String[] extensions;
        switch (fileType) {
            case TYPE_VIDEO:
                extensions = confirmedVideoExtensions;
                break;
            case TYPE_AUDIO:
                extensions = confirmedAudioExtensions;
                break;
            default :
                extensions = new String[0];
                break;
        }
        String[] tmpSplitFileName = fileName.split("\\.");
        String fileExtension = tmpSplitFileName[ tmpSplitFileName.length - 1 ];
        for (String extension : extensions) {
            if(fileExtension.equals(extension)) return true;
        }
        return false;
    }

}
