package com.estsoft.muvigram.ui.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jaylim on 10/31/2016.
 */

public class CameraFragment extends BaseSingleFragment implements CameraView {

    @Inject CameraPresenter mPresenter;

    private static final String TAG = "Camera2VideoFragment";

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @BindView(R.id.camera_surface_view) SurfaceView mCameraSurfaceView;
    @BindView(R.id.camera_camera_shutter) ImageButton mCameraShutter;
    @BindView(R.id.camera_switch_view) ImageButton mSwitchView;
    @BindView(R.id.camera_flash_control) ImageButton mFlashControl;

    @OnClick(R.id.camera_surface_view)
    public void autoFocusing(SurfaceView surfaceView) {

    }

    @OnClick(R.id.camera_camera_shutter)
    public void controlRecord(ImageButton imageButton) {
        mPresenter.recordVideo();
    }

    @OnClick(R.id.camera_flash_control)
    public void controlFlash(ImageButton imageButton) {
        mPresenter.controlFlash();
    }

    @OnClick(R.id.camera_switch_view)
    public void switchView(ImageButton imageButton) {
        mPresenter.switchView();
    }

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /* Business - View Logic */

    @Override public void startRecording() {
        Toast.makeText(getActivity(), "Start Recording", Toast.LENGTH_SHORT).show();
    }

    @Override public void endRecording() {
        Toast.makeText(getActivity(), "End Recording", Toast.LENGTH_SHORT).show();
    }

    @Override public void switchToSelfieView() {
        Toast.makeText(getActivity(), "[View] Camera -> Selfie", Toast.LENGTH_SHORT).show();
    }

    @Override public void switchToCameraView() {
        Toast.makeText(getActivity(), "[View] Selfie -> Camera", Toast.LENGTH_SHORT).show();
    }

    @Override public void turnOnFlash() {
        Toast.makeText(getActivity(), "[Flash] Off -> On", Toast.LENGTH_SHORT).show();
    }

    @Override public void automateFlash() {
        Toast.makeText(getActivity(), "[Flash] On -> Auto", Toast.LENGTH_SHORT).show();
    }

    @Override public void turnOffFlash() {
        Toast.makeText(getActivity(), "[Flash] Auto -> Off", Toast.LENGTH_SHORT).show();
    }
}
