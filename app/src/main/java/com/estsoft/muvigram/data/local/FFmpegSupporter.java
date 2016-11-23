package com.estsoft.muvigram.data.local;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.estsoft.muvigram.injection.qualifier.ApplicationContext;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import org.m4m.AudioFormat;
import org.m4m.IProgressListener;
import org.m4m.MediaComposer;
import org.m4m.MediaFile;
import org.m4m.MediaFileInfo;
import org.m4m.VideoFormat;
import org.m4m.android.AndroidMediaObjectFactory;
import org.m4m.android.AudioFormatAndroid;
import org.m4m.android.VideoFormatAndroid;
import org.m4m.domain.MediaCodecInfo;
import org.m4m.domain.Pair;
import org.m4m.effects.RotateEffect;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by estsoft on 2016-10-26.
 */
public class FFmpegSupporter {
    private static final String TAG = "FFmpegSupporter";

    //M4M
    final String VIDEO_MIME_TYPE = "video/avc";
    final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    final int VIDEO_BIT_RATE_K = 2000;
    final int VIDEO_FRAME_RATE = 30;
    final int VIDEO_FRAME_INTERVAL = 1;
    final int AUDIO_BITRATE = 96 * 1024;
    final int AUDIO_CODEC_PROFILE_LEVEL = MediaCodecInfo.CodecProfileLevel.AACObjectLC;

    AndroidMediaObjectFactory factory;

    //FFMPEG
    LoadBinaryResponseHandlerImpl mBinaryLoadingHandler;
    final FFmpeg ffmpeg;

    @Inject
    public FFmpegSupporter(@ApplicationContext Context context) {

        factory = new AndroidMediaObjectFactory( context );

        ffmpeg = FFmpeg.getInstance(context);
        mBinaryLoadingHandler = new LoadBinaryResponseHandlerImpl();
        try {
            ffmpeg.loadBinary(mBinaryLoadingHandler);
        } catch ( FFmpegNotSupportedException e ) {
            e.printStackTrace();
        }

    }

    public Observable<Integer> cutVideoM4M (String videoPath, int startTimeMs, int runTimeMs, String targetPath ) throws IOException, FileNotSupportException {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        M4mProgressListener progressListener = new M4mProgressListener( publishSubject );

            MediaComposer mediaComposer = getMediaComposerForTranscode(progressListener, videoPath, targetPath);
            MediaFileInfo fileInfo = getMediaInformation(videoPath);

            VideoFormat videoFormat = (VideoFormat)fileInfo.getVideoFormat();
            VideoFrameMatrix matrix = new VideoFrameMatrix( videoFormat.getVideoFrameSize().width(), videoFormat.getVideoFrameSize().height(), fileInfo.getRotation() );
            AudioFormat audioFormat = (AudioFormat)fileInfo.getAudioFormat();
            try {
                configureVideoEncoder(mediaComposer, matrix.calWidth, matrix.calHeight, matrix.calRotation);
                configureAudioEncoder(mediaComposer, audioFormat);
            } catch ( NullPointerException e ) {
                throw new FileNotSupportException();
            }

            // Segment Trimming
            MediaFile mediaFile = mediaComposer.getSourceFiles().get(0);
            long microSegmentFrom = startTimeMs * 1000L;
            long microSegmentTo = runTimeMs * 1000L;
            mediaFile.addSegment( new Pair<>( microSegmentFrom, microSegmentTo ) );
            mediaComposer.start();

        return publishSubject;
    }

    private MediaComposer getMediaComposerForTranscode( IProgressListener listener, String sourceFile, String targetPath ) throws IOException {
        MediaComposer mediaComposer = new MediaComposer( factory, listener);
        mediaComposer.addSourceFile( sourceFile );
        mediaComposer.setTargetFile( targetPath );
        return mediaComposer;
    }
    private MediaFileInfo getMediaInformation( String inputFile ) throws IOException {
        MediaFileInfo info = new MediaFileInfo(factory);
        info.setFileName(inputFile);
        return info;
    }
    private void configureVideoEncoder( MediaComposer mediaComposer, int width, int height, int rotation ) {
        VideoFormatAndroid videoFormat = new VideoFormatAndroid( VIDEO_MIME_TYPE, width, height );
        videoFormat.setVideoBitRateInKBytes( VIDEO_BIT_RATE_K );
        videoFormat.setVideoFrameRate(VIDEO_FRAME_RATE);
        videoFormat.setVideoIFrameInterval(VIDEO_FRAME_INTERVAL);
        mediaComposer.setTargetVideoFormat(videoFormat);
        mediaComposer.addVideoEffect( new RotateEffect( rotation, factory.getEglUtil() ));
    }
    private void configureAudioEncoder(MediaComposer mediaComposer, AudioFormat sourceAudioFormat ) {
        AudioFormatAndroid audioFormat = new AudioFormatAndroid( AUDIO_MIME_TYPE, sourceAudioFormat.getAudioSampleRateInHz(), sourceAudioFormat.getAudioChannelCount() );
        audioFormat.setAudioBitrateInBytes( AUDIO_BITRATE );
        audioFormat.setAudioProfile(AUDIO_CODEC_PROFILE_LEVEL);
        mediaComposer.setTargetAudioFormat(audioFormat);
    }
    private class M4mProgressListener implements org.m4m.IProgressListener {
        PublishSubject<Integer> subject;

        public M4mProgressListener(PublishSubject<Integer> subject) {
            this.subject = subject;
        }

        @Override
        public void onMediaStart() {
            subject.onNext( 0 );
        }

        @Override
        public void onMediaProgress(float progress) {
            subject.onNext( (int)(progress * 100) );
        }

        @Override
        public void onMediaDone() {
            subject.onCompleted();
        }

        @Override
        public void onMediaPause() {

        }

        @Override
        public void onMediaStop() {

        }

        @Override
        public void onError(Exception exception) {
            subject.onError( exception );
        }
    }
    private class VideoFrameMatrix {
        int width, height, rotation;
        int calWidth, calHeight, calRotation;

        VideoFrameMatrix(int w, int h, int r) {
            Log.d(TAG, "VideoFrameMatrix: " + " w ... " + w  +  " / h ... " + h + " / r ... " + r);
            this.width = w; this.height = h; this.rotation = r;
            calWidth = 720;
            if ( width > height ) {
                calRotation = rotation == 0 || rotation == 180 ? rotation + 90 : rotation;
                calHeight = width * calWidth / height;
            } else {
                calRotation = rotation;
                calHeight = height * calWidth / width;
            }
        }

    }


    public Observable<Integer> cutAudio(String audioPath, int offsetMs, int runtimeBuffer, String targetPath) throws FFmpegCommandAlreadyRunningException {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        String[] cliCommand = Command.getInstance()
                .input(audioPath)
                .offsetMs(String.valueOf(offsetMs))
                .runtime(String.valueOf(runtimeBuffer))
                .audioCodecCopy()
                .target(targetPath)
                .build();
        ffmpeg.execute(cliCommand, new ExecuteResponseHandlerImpl(publishSubject));
        return publishSubject;
    }
    public Observable<Integer> muxVideoAndAudio(String videoPath, String audioPath, float videoVolume, float audioVolume, String targetPath ) {
        String[] cliCommand = Command.getInstance()
                .input(videoPath)
                .input(audioPath)
                .shortest()
                .videoMap()
                .videoCodecCopy()
                .filterComplexAudioVolmue(videoVolume, audioVolume)
                .audioCodec("acc")
                .target(targetPath)
                .build();


        return null;
    }

    private class ExecuteResponseHandlerImpl implements FFmpegExecuteResponseHandler {
        PublishSubject<Integer> subject;
        int progress = 0;
        public ExecuteResponseHandlerImpl(PublishSubject<Integer> subject) {
            this.subject = subject;
        }
        @Override
        public void onSuccess(String message) {
            Log.d(TAG, "onSuccess: " + message);
            subject.onCompleted();
        }

        @Override
        public void onProgress(String message) {
            message = message.trim();
            if (message.startsWith("size")) {
                int start = message.indexOf("time");
                try {
                    progress = Integer.valueOf(message.substring(start + 11, start + 13));
                } catch ( Exception e) {
                    e.printStackTrace();
                    progress = 0;
                }
                subject.onNext(progress);
            }
            Log.d(TAG, "onProgress: " + message);
        }

        @Override
        public void onFailure(String message) {
            Log.d(TAG, "onFailure: " + message);
            subject.onError( new Exception() );
        }

        @Override
        public void onStart() {
            subject.onNext( progress ++ );
        }

        @Override
        public void onFinish() {
        }
    }
    private class LoadBinaryResponseHandlerImpl implements FFmpegLoadBinaryResponseHandler {
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

    private static class Command {
        private String commandLine;
        private final String SPACE = " ";
        private static Command instance;

        public static Command getInstance() {
            if (instance == null) instance = new Command();
            instance.initCommand();
            return instance;
        }
        private Command() {}

        private String initCommand() { commandLine = ""; return commandLine; }
        public Command input( String path ) { commandLine += SPACE + "-i" + SPACE + path; return this; }
        public Command target( String path ) { commandLine += SPACE + "-y" + SPACE + path; return this; }
        public Command offsetMs( String offsetMs ) { commandLine += SPACE + "-ss" +SPACE + cutMsToTimeStamp(offsetMs); return this; }
        public Command runtime( String runtime ) { commandLine += SPACE + "-t" + SPACE + runtime; return this; }
        public Command shortest() { commandLine += SPACE + "-shortest"; return this; }
        public Command videoMap() { commandLine += SPACE + "-map" + SPACE + "0:v:0"; return this; }
        public Command videoCodecCopy() { commandLine += SPACE + "-c:v" + SPACE + "copy"; return this; }
        public Command audioCodecCopy() { commandLine += SPACE + "-c:a" + SPACE + "copy"; return this; }
        public Command audioCodec( String codec ) { commandLine += SPACE + "-c:a" + SPACE + codec; return this; }
        public Command filterComplexAudioVolmue(float firstVolume, float secondVolume) {
            commandLine += SPACE + "-filter_complex"
                    + SPACE + "\"[0:a:0]volume=" + String.valueOf(firstVolume) + "[a0];"
                    + SPACE + "[1:a:0]volume=" + String.valueOf(secondVolume) + "[a1];"
                    + SPACE + "[a0][a1]amix=inputs=2[out]\""
                    + SPACE + "-map" + SPACE + "\"[out]\""
                    + SPACE + "-ac" + SPACE + "2";
            return this;
        }


        public String[] build() {
            Log.d(TAG, "build: " + commandLine ); return commandLine.trim().split(SPACE); }

        private String cutMsToTimeStamp( String timeMs ) {
            int time = Integer.valueOf(timeMs);
//        timeMs = timeMs - 100 < 0 ? 0 : timeMs - 100;
            String[] result = new String[3];
            int ms = time % 1000;
            result[0] = ms > 100 ? (ms / 10) + "" : ms + "";
            int sec = (time / 1000) % 60;
            result[1] = sec < 10 ? "0" + sec : "" + sec;
            int min = (time / 1000) / 60;
            result[2] = min < 10 ? "0" + min : "" + min;
            Log.d(TAG, "cutMsToTimeStamp: " + result[2] + " / " + result[1] + " / " + result[0]);
            return "00:" + result[2] + ":" + result[1] + "." + result[0] ;
        }
    }

//    public void cutAudio(String audioPath, int startTimeMs, int runTimeSeconds ) {
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

//    }

    public Observable<String> cutVideo(String videoPath, int startTimeMs, int runTimeSeconds) {
//        Log.d(TAG, "cutVideo: " + startTimeMs);
//        String[] times = cutMsToTimeStamp(startTimeMs);
//        String startTime = String.valueOf(Math.round(startTimeMs / 1000.0));
////        String cliCommend = "-ss START_TIME -i VIDEO_PATH -c copy -t RUNTIME -y STORAGE_PATH/RESULT_FILE";
////        String cliCommend = "-ss START_TIME -i VIDEO_PATH -vf scale=480:-2 -b:v 1M -threads 0 -t RUNTIME -y STORAGE_PATH/RESULT_FILE";
//        String cliCommend = "-i VIDEO_PATH -vf scale=480:-2 -preset superfast -y STORAGE_PATH/RESULT_FILE";
//        cliCommend = cliCommend.replace("START_TIME","00:" + times[2] +":" + times[1] +"." + times[0]);
//        cliCommend = cliCommend.replace("VIDEO_PATH", videoPath);
//        cliCommend = cliCommend.replace("RUNTIME", runTimeSeconds + "");
//        cliCommend = cliCommend.replace("STORAGE_PATH", mEditDir.getAbsolutePath());
//        cliCommend = cliCommend.replace("RESULT_FILE", CUT_FILE_NAME);
//        Log.d(TAG, "splitVideo: completed commend : " + cliCommend);
//
//        try {
//            ffmpeg.execute(cliCommend.split(" "), mHandler);
//        } catch (FFmpegCommandAlreadyRunningException e) {
//            e.printStackTrace();
//        }
//
//
//        return Observable.create( subscriber -> {
//            while (true) {
//                subscriber.onNext("Progressing");
//                if (status == FINISHED) {
//                    subscriber.onNext(mEditDir.getAbsolutePath() + "/" + CUT_FILE_NAME);
//                    status = PREPARE;
//                    break;
//                }
//                try {
//                    Thread.sleep(100);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            subscriber.onCompleted();
//        });
        return null;
    }
}
