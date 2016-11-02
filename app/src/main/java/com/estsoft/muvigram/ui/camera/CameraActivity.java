package com.estsoft.muvigram.ui.camera;

import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

public class CameraActivity extends BaseSingleFragmentActivity {

    @Override
    protected Fragment createDefaultFragment() {
        return CameraFragment.newInstance();
    }

}
