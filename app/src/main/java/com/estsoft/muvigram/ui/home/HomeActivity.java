package com.estsoft.muvigram.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;
import com.estsoft.muvigram.ui.camera.CameraActivity;
import com.estsoft.muvigram.ui.selectmusic.MusicSelectActivity;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class HomeActivity extends BaseActivity implements HomeView, SpaceOnClickListener {

    @Inject HomePresenter mHomePresenter;
    private SweetSheet mSweetSheet;

    @BindView(R.id.home_space_navigation) SpaceNavigationView mSpaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mHomePresenter.attachView(this);

        initBottomSheetView();
        initSpaceNavigationView(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.detachView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSpaceNavigationView.onSaveInstanceState(outState);
    }

    /***** View init **/

    private void initBottomSheetView()
    {
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.activity_home);
        mSweetSheet = new SweetSheet(frameLayout);
        CustomDelegate customDelegate = new CustomDelegate(false,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        final View view = LayoutInflater.from(this).inflate(R.layout.home_bottom_sheet, null, false);
        customDelegate.setCustomView(view);
        mSweetSheet.setDelegate(customDelegate);

        view.findViewById(R.id.sheet_camera_button).setOnClickListener(
                v -> startActivity(new Intent(this, CameraActivity.class))
        );
        view.findViewById(R.id.sheet_music_button).setOnClickListener(
                v -> startActivity(new Intent(this, MusicSelectActivity.class))
        );
    }

    private void initSpaceNavigationView(Bundle savedInstanceState)
    {
        // space navigation view
        final Resources res = getResources();
        mSpaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item), R.drawable.ic_action_home));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item1), R.drawable.ic_action_search));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item2), R.drawable.ic_action_noty));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item3), R.drawable.ic_action_info));
        mSpaceNavigationView.setCentreButtonIcon(R.drawable.ic_action_camera);
        mSpaceNavigationView.showIconOnly();
        mSpaceNavigationView.setSpaceOnClickListener(this);
    }

    /***** SpaceOnClickListener **/

    @Override
    public void onCentreButtonClick() {
        mSweetSheet.show();
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        mHomePresenter.loadTestData();
    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {

    }

    /***** MVP View methods implementation *****/

    @Override
    public void showNetworkError() {
        Timber.e("error");
    }

    @Override
    public void showTestToast(int i) {
        Timber.e("showTestToast  " + i);
    }
}
