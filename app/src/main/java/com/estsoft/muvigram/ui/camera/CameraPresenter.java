package com.estsoft.muvigram.ui.camera;

import com.estsoft.muvigram.injection.ConfigPersistent;
import com.estsoft.muvigram.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by jaylim on 10/31/2016.
 */

@ConfigPersistent
public class CameraPresenter extends BasePresenter<CameraView> {

    CameraView mCameraView;

    @Inject
    public CameraPresenter() {
    }

    @Override
    public void attachView(CameraView mvpView) {
        super.attachView(mvpView);
        mCameraView = getMvpView();
    }

    @Override
    public void detachView() {
        super.detachView();
        mCameraView = null;
    }




    private boolean isRecording = false;

    public void recordVideo() {
        if (isRecording) {
            endRecording();
        } else {
            startRecording();
        }

        isRecording = !isRecording;
    }

    private void startRecording() {
        mCameraView.startRecording();
    }

    private void endRecording() {
        mCameraView.endRecording();
    }

    private final static int VIEW_FRONT_SELFIE = 0;
    private final static int VIEW_REAR_CAMERA  = 1;

    private int mCurViewMode = VIEW_FRONT_SELFIE;

    public void switchView() {

        switch(mCurViewMode) {
            case VIEW_FRONT_SELFIE :
                mCameraView.switchToCameraView();
                break;
            case VIEW_REAR_CAMERA :
                mCameraView.switchToSelfieView();
                break;
            default:
                break;
        }
        mCurViewMode = (mCurViewMode + 1) % 2;
    }

    private final static int FLASH_OFF  = 0;
    private final static int FLASH_ON   = 1;
    private final static int FLASH_AUTO = 2;

    private int mCurFlashMode = FLASH_OFF;

    public void controlFlash() {

        switch(mCurFlashMode) {
            case 0 :
                mCameraView.turnOnFlash();
                break;
            case 1 :
                mCameraView.automateFlash();
                break;
            case 2 :
                mCameraView.turnOffFlash();
                break;
            default :
                break;
        }
        mCurFlashMode = (mCurFlashMode + 1) % 3;
    }
}
