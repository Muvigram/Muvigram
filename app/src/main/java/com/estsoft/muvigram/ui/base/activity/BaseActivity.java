package com.estsoft.muvigram.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.injection.component.PlainActivityComponent;
import com.estsoft.muvigram.injection.component.ApplicationComponent;
import com.estsoft.muvigram.injection.component.ConfigPersistentComponent;
import com.estsoft.muvigram.injection.component.DaggerConfigPersistentComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;


/**
 * Activity that every ather activity in this application must implement.
 *
 * It handles
 *   1. Creation of dagger components
 *   2. Makes sure that instances of {@link ConfigPersistentComponent}
 *      survive across the configuration change. (NOT PlainActivityComponent.)
 *
 * It provides
 *   1. Access to the {@link PlainActivityComponent}.
 */
public class BaseActivity extends AppCompatActivity {

    /* KV pair : KEY_ACTIVITIY_ID -> mActivityId -> mActivityComponent */
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private long mActivityId;
    private ConfigPersistentComponent mConfigPersistentComponent;

    /* Where the key is stored. */
    private static final Map<Long, ConfigPersistentComponent> sComponentMap = new HashMap<>();

    /* Async key generator */
    private static final AtomicLong NEXT_ID = new AtomicLong(0);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // If the BaseActivity is getting into the finishing step, save the activity id.
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* [get] KEY_ACTIVITY_ID -> mActivityId or [generate] new mActivityId */
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();

        /* [get] mActivityId -> mActivityComponent or [generate] new configPersistentActivityComponent. */
        if (sComponentMap.containsKey(mActivityId)) { /* Get existing key */
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = sComponentMap.get(mActivityId);

        } else { /* GENERATE AND SAVE */
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            mConfigPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(getApplicationComponent()).build();

            sComponentMap.put(mActivityId, mConfigPersistentComponent);
        }


    }

    @Override
    protected void onDestroy() {
        // If the view is destroyed due to the configuration change,
        // leave the saved component, else remove it.
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentMap.remove(mActivityId);
        }
        super.onDestroy();
    }


    protected ConfigPersistentComponent getConfigPersistentComponent() {
        return mConfigPersistentComponent;
    }

    /** Do not use in any activity but only {@link BaseActivity} */
    private ApplicationComponent getApplicationComponent() {
        return MuvigramApplication.get(this).getApplicationComponent();
    }
}
