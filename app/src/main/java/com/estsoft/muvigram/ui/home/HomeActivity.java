package com.estsoft.muvigram.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;
import com.estsoft.muvigram.ui.camera.CameraActivity;
import com.estsoft.muvigram.ui.feed.FeedFragment;
import com.estsoft.muvigram.ui.notify.NotifyFragment;
import com.estsoft.muvigram.ui.profile.ProfileFragment;
import com.estsoft.muvigram.ui.search.SearchFragment;
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

/**
 * The type Home activity.
 */
public class HomeActivity extends BaseActivity implements HomeView, SpaceOnClickListener {

    /**
     * The M home presenter.
     */
    @Inject HomePresenter mHomePresenter;
    @BindView(R.id.background) FrameLayout background;

    private SweetSheet mSweetSheet;
    private FragmentManager mFragmentManager;
    private FeedFragment mFeedFragment = null;
    private SearchFragment mSearchFragment = null;
    private NotifyFragment mNotifyFragment = null;
    private ProfileFragment mProfileFragment = null;

    /**
     * The M space navigation view.
     */
    @BindView(R.id.home_space_navigation) SpaceNavigationView mSpaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mHomePresenter.attachView(this);


        initBottomSheetView();
        initSpaceNavigationView(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_home);

        if(fragment == null){
            mFeedFragment = new FeedFragment();
            fragment = mFeedFragment;
            fm.beginTransaction().add(R.id.activity_home, fragment).commit();
        }
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
        mSpaceNavigationView.setSpaceBackgroundColor(Color.TRANSPARENT);
        mSpaceNavigationView.setActiveSpaceItemColor(getResources().getColor(R.color.blue_grey_700));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item), R.drawable.ic_action_home));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item1), R.drawable.ic_action_search));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item2), R.drawable.ic_action_noty));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(res.getString(R.string.home_space_item3), R.drawable.ic_action_info));
        mSpaceNavigationView.setCentreButtonIcon(R.drawable.ic_action_camera);
        mSpaceNavigationView.showIconOnly();
        mSpaceNavigationView.setSpaceOnClickListener(this);
    }

    @Override
    public void onCentreButtonClick() {
        mSweetSheet.show();
    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        mHomePresenter.loadTestData();

        mFragmentManager = getSupportFragmentManager();

        if(0 == itemIndex) {
            mSpaceNavigationView.changeSpaceBackgroundColor(Color.TRANSPARENT);
        } else {
            mSpaceNavigationView.changeSpaceBackgroundColor(getResources().getColor(R.color.grey_800));
        }

        switch(itemIndex){
            case 0:
                if(mFeedFragment == null) {
                    mFeedFragment = new FeedFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mFeedFragment).commit();
                break;
            case 1:
                if(mSearchFragment == null) {
                    mSearchFragment = new SearchFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mSearchFragment).commit();
                //getWindow().setStatusBarColor(getResources().getColor(R.color.light_blue_300));
                break;
            case 2:
                if(mNotifyFragment == null) {
                    mNotifyFragment = new NotifyFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mNotifyFragment).commit();
                //getWindow().setStatusBarColor(getResources().getColor(R.color.light_blue_300));

                break;
            case 3:
                if(mProfileFragment == null) {
                     mProfileFragment = new ProfileFragment();
                }
                mFragmentManager.beginTransaction().replace(R.id.activity_home, mProfileFragment).commit();
                //getWindow().setStatusBarColor(getResources().getColor(R.color.light_blue_300));
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {

    }

    @Override
    public void showNetworkError() {
        Timber.e("error");
    }

    @Override
    public void showTestToast(int i) {
        Timber.e("showTestToast  " + i);
    }
}
