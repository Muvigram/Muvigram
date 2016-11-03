package com.estsoft.muvigram.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.spacebar.TransParentSpaceView;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.camera.CameraActivity;
import com.estsoft.muvigram.ui.feed.FeedFragment;
import com.estsoft.muvigram.ui.notify.NotifyFragment;
import com.estsoft.muvigram.ui.profile.ProfileFragment;
import com.estsoft.muvigram.ui.search.SearchFragment;
import com.estsoft.muvigram.ui.musicselect.MusicSelectActivity;
import com.estsoft.muvigram.ui.videoselect.VideoSelectActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * The type Home activity.
 */
public class HomeActivity extends BasePlainActivity implements HomeView, TransParentSpaceView.OnSpaceViewListener {

    @BindView(R.id.home_trans_navigation) TransParentSpaceView mTransSpaceView;
    @Inject HomePresenter mHomePresenter;
    @BindView(R.id.background) FrameLayout background;

    private BottomSheetDialog mBottomSheetDialog;
    private FragmentManager mFragmentManager;
    private FeedFragment mFeedFragment = null;
    private SearchFragment mSearchFragment = null;
    private NotifyFragment mNotifyFragment = null;
    private ProfileFragment mProfileFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlainActivityComponent().inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mHomePresenter.attachView(this);

        initBottomSheetView();
        initTransSpaceView(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_home);

        if (fragment == null) {
            mFeedFragment = new FeedFragment();
            fragment = mFeedFragment;
            fm.beginTransaction().add(R.id.activity_home, fragment).commit();
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initBottomSheetView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.camera_bottom_sheet, null, false);
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);

        view.findViewById(R.id.sheet_camera_layout).setOnClickListener(
                v -> startActivity(new Intent(this, CameraActivity.class))
        );
        view.findViewById(R.id.sheet_music_layout).setOnClickListener(
                v -> startActivity(new Intent(this, MusicSelectActivity.class))
        );
        view.findViewById(R.id.sheet_library_layout).setOnClickListener(
                v -> startActivity(new Intent(this, VideoSelectActivity.class))
        );

    }

    private void initTransSpaceView(Bundle savedInstanceState) {
        // space navigation view
        mTransSpaceView.addSpaceBarIcon(R.drawable.ic_action_home);
        mTransSpaceView.addSpaceBarIcon(R.drawable.ic_action_search);
        mTransSpaceView.addSpaceBarIcon(R.drawable.ic_action_noty);
        mTransSpaceView.addSpaceBarIcon(R.drawable.ic_action_info);
        mTransSpaceView.setOnSpaceViewListener(this);
    }

    /*test*/
    public void showNetworkError() {
        Timber.e("showNetworkError");
    }

    @Override
    public void showTestToast(int i) {

    }
    /*test*/

    @Override public void onCenterClick() {
        mBottomSheetDialog.show();
    }

    @Override public void onSpaceTabClick(int index) {
        mHomePresenter.loadTestData();

        mFragmentManager = getSupportFragmentManager();

        switch (index) {
            case 0:
                if (mFeedFragment == null) {
                    mFeedFragment = new FeedFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mFeedFragment).commit();
                break;
            case 1:
                if (mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mSearchFragment).commit();
                break;
            case 2:
                if (mNotifyFragment == null) {
                    mNotifyFragment = new NotifyFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mNotifyFragment).commit();

                break;
            case 3:
                if (mProfileFragment == null) {
                    mProfileFragment = new ProfileFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mProfileFragment).commit();
                break;
            default:
                break;
        }
    }
}
