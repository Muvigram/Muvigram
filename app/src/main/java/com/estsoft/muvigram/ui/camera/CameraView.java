package com.estsoft.muvigram.ui.camera;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface CameraView extends MvpView {

    void startRecording();

    void endRecording();

    void switchToSelfieView();

    void switchToCameraView();

    void turnOnFlash();

    void automateFlash();

    void turnOffFlash();
}
