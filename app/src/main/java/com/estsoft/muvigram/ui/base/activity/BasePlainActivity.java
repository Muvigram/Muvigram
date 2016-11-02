package com.estsoft.muvigram.ui.base.activity;

import android.os.Bundle;

import com.estsoft.muvigram.injection.component.PlainActivityComponent;
import com.estsoft.muvigram.injection.module.PlainActivityModule;

/**
 * Created by jaylim on 10/31/2016.
 */

public class BasePlainActivity extends BaseActivity {

    private PlainActivityComponent mPlainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get Activity sub-component
        mPlainActivityComponent = getConfigPersistentComponent().plus(new PlainActivityModule(this));
    }

    public PlainActivityComponent getPlainActivityComponent() {
        return mPlainActivityComponent;
    }
}
