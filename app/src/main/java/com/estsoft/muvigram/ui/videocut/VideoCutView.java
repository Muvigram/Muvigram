package com.estsoft.muvigram.ui.videocut;

import android.graphics.Bitmap;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface VideoCutView extends MvpView {

    int TASK_THUMBNAIL = -90;
    int TASK_TRANSCODE = -91;
    int ERROR_FILE_NOT_SUPPORT = -80;
    int ERROR_ANONYMOUS = -81;

    /* View logic interface here ... */
    void checkLayoutMeasured();

    void updateThumbnails(Bitmap bitmap);
    void restartVideoAt( int ms );
    void providingRuntime();

    void enableProgress( int max );
    void updateProgress( int percent, int key );
    void disableProgress();
    void nextActivity(String cutAudioPath);

    void onError( int key );
}
