package com.estsoft.muvigram.data.local;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.estsoft.muvigram.injection.qualifier.ApplicationContext;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by estsoft on 2016-10-26.
 */
@Singleton
public class FFmpegSupporter {
    private static final String TAG = "FFmpegSupporter";

    final String CUT_FILE_NAME = "cut_result.mp4";
    final String MEDIA_EDIT_DIR = "/edit";
    final String DCIM_CAMERA_PATH;
    final String APP_FILES_DIR_PATH;
    final FFmpeg ffmpeg;
    final File mEditDir;

    final static int FAILED = -11;
    final static int PROGRESS = -12;
    final static int SUCCESS = -13;
    final static int FINISHED = -14;
    final static int PREPARE = -15;
    int status = PREPARE;

    ExecuteResponseHandlerImpl mHandler;
    LoadBinaryResponseHandlerImpl mBinaryLoadingHandler;

    @Inject
    public FFmpegSupporter(@ApplicationContext Context context) {
        ffmpeg = FFmpeg.getInstance(context);
        mBinaryLoadingHandler = new LoadBinaryResponseHandlerImpl();
        try {
            ffmpeg.loadBinary(mBinaryLoadingHandler);
        } catch ( FFmpegNotSupportedException e ) {
            e.printStackTrace();
        }

        DCIM_CAMERA_PATH = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM ).getAbsolutePath() + "/Camera";
        APP_FILES_DIR_PATH = context.getExternalFilesDir(null).getAbsolutePath();
        mEditDir = new File(APP_FILES_DIR_PATH + MEDIA_EDIT_DIR);
        if (!mEditDir.exists()) mEditDir.mkdirs();

        mHandler = new ExecuteResponseHandlerImpl();
    }

    public Observable<String> cutVideo(String videoPath, int startTimeMs, int runTimeSeconds) {
        String startTime = String.valueOf(Math.round(startTimeMs / 1000.0));
        String cliCommend = "-ss START_TIME -i VIDEO_PATH -c copy -y -t RUNTIME STORAGE_PATH/RESULT_FILE";
        cliCommend = cliCommend.replace("START_TIME",startTime);
        cliCommend = cliCommend.replace("VIDEO_PATH", videoPath);
        cliCommend = cliCommend.replace("RUNTIME", runTimeSeconds + "");
        cliCommend = cliCommend.replace("STORAGE_PATH", mEditDir.getAbsolutePath());
        cliCommend = cliCommend.replace("RESULT_FILE", CUT_FILE_NAME);
        Log.d(TAG, "splitVideo: completed commend : " + cliCommend);

        try {
            ffmpeg.execute(cliCommend.split(" "), mHandler);
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
        }

        return Observable.create( subscriber -> {
            while (true) {
                subscriber.onNext("Progressing");
                if (status == FINISHED) {
                    subscriber.onNext(mEditDir.getAbsolutePath() + "/" + CUT_FILE_NAME);
                    status = PREPARE;
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            subscriber.onCompleted();
        });
    }

    class ExecuteResponseHandlerImpl implements FFmpegExecuteResponseHandler {
        @Override
        public void onSuccess(String message) {
            Log.d(TAG, "onSuccess: " + message);
            status = SUCCESS;
        }

        @Override
        public void onProgress(String message) {
            Log.d(TAG, "onProgress: " + message);
            status = PROGRESS;
        }

        @Override
        public void onFailure(String message) {
            status = FAILED;
            Log.d(TAG, "onFailure: " + message);
        }

        @Override
        public void onStart() {
            Log.d(TAG, "onStart: ");
        }

        @Override
        public void onFinish() {
            status = FINISHED;
            Log.d(TAG, "onFinish: ");
        }
    }
    class LoadBinaryResponseHandlerImpl implements FFmpegLoadBinaryResponseHandler {
        @Override
        public void onFailure() {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onFinish() {

        }
    }

    public void cutAudio(String audioPath, int startTimeMs, int runTimeSeconds ) {
//        runTimeSeconds --;
//        String cliCommand = "-i AUDIO_PATH -ss START_TIME -t RUNTIME -codec copy -y STORAGE_PATH/cut_result.mp3";
//        cliCommand = cliCommand.replace("AUDIO_PATH", audioPath);
//        String startTime = String.valueOf((float)(startTimeMs / 1000.0 ));
//        cliCommand = cliCommand.replace("START_TIME", startTime);
//        cliCommand = cliCommand.replace("RUNTIME", runTimeSeconds + "");
//        final String storePath = StorageManager.getEditWorkingDir();
//        cliCommand = cliCommand.replace("STORAGE_PATH", storePath);
//        Log.d(TAG, "splitVideo: completed commend : " + cliCommand);
//
//        try {
//            ffmpeg.execute(cliCommand.split(" "), new FFmpegExecuteResponseHandler() {
//                @Override
//                public void onSuccess(String message) {
//                    Log.d(TAG, "onSuccess: " + message);
//                }
//
//                @Override
//                public void onProgress(String message) {
//                    Log.d(TAG, "onProgress: " + message);
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Log.d(TAG, "onFailure: " + message);
//                }
//
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }




}
