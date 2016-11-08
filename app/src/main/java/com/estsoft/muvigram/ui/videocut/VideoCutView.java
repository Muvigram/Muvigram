package com.estsoft.muvigram.ui.videocut;

import android.graphics.Bitmap;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface VideoCutView extends MvpView {

    int CUT_STANDARD_MILLISECOND = 15000;
    int MILLI_WEIGHT = 1000;

    /* View logic interface here ... */
    void checkLayoutMeasured();

    void updateThumbnails(Bitmap bitmap);
    void restartVideoAt( int ms );
    void providingRuntime();

    void enableProgress();
    void disableProgress();
    void nextActivity(String cutAudioPath);

    void onError();
}
