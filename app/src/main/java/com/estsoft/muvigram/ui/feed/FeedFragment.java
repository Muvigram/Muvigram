package com.estsoft.muvigram.ui.feed;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.MusicRecordView;
import com.estsoft.muvigram.customview.transparentview.TabView;
import com.estsoft.muvigram.customview.transparentview.TransParentTabView;
import com.estsoft.muvigram.customview.viewpager.RecyclerViewPager;
import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.model.FeedRepo;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.estsoft.muvigram.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by jang gong ui on 2016. 10. 17..
 */

public class FeedFragment extends Fragment implements TransParentTabView.OnTabItemClickListener, FeedRepo.ItemCallback, FeedView {


    @BindView(R.id.trnasparent_tab) TransParentTabView mFeedTabView;
    @BindView(R.id.pager) RecyclerViewPager mRecyclerViewPager;
    @BindView(R.id.musicRecordView) MusicRecordView mMusicRecordView;
    @Inject FeedPresenter mFeedPresenter;

    private LinearLayoutManager mLayoutManager;
    private ListItemsVisibilityCalculator mListItemVisibilityCalculator;
    private ItemsPositionGetter mItemsPositionGetter;
    private ArrayList<FeedRepo> mFeedRepos = new ArrayList<>();
    private FeedAdapter mFeedAdapter;
    private int mScrollState;


    // TODO: dagger inject and ButterKnife, Presenter init
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final ParentFragmentComponent mParentFragmentComponent
                = ((HomeActivity) getActivity()).getSingleFragmentActivityComponent(this);
        mParentFragmentComponent.inject(this);
        final View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        mFeedPresenter.attachView(this);
        return view;
    }

    // TODO: view initialization
    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        initTransParentTab();
        initMusicRecordView();
        mFeedPresenter.loadFeedRepos();
    }

    @Override public void onResume() {
        super.onResume();
        // the first item stat signal for a recyclerView .
        if (!mFeedRepos.isEmpty()) {
            mRecyclerViewPager.post(() -> mListItemVisibilityCalculator.onScrollStateIdle(
                    mItemsPositionGetter,
                    mLayoutManager.findFirstVisibleItemPosition(),
                    mLayoutManager.findLastVisibleItemPosition()));
        }
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mFeedPresenter.detachView();
    }

    private void initViewPager() {
        mFeedRepos = getDummyFeedRepos();
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        //mLayoutManager = new PreCachingLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        //mLayoutManager.setExtraLayoutSpace(ViewUtils.getDisplayPerHeight(getContext(), 5));
        mListItemVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mFeedRepos);

        mFeedAdapter = new FeedAdapter(getContext(), mFeedRepos);

        mRecyclerViewPager.setSinglePageFling(true);
        mRecyclerViewPager.setLayoutManager(mLayoutManager);
        mRecyclerViewPager.setHasFixedSize(true);
        mRecyclerViewPager.setLongClickable(true);
        mRecyclerViewPager.setAdapter(mFeedAdapter);
        mRecyclerViewPager.setItemViewCacheSize(10);
        mRecyclerViewPager.addOnScrollListener(mScrollListener);
        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerViewPager);
    }

    private void initTransParentTab() {

        final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mFeedTabView.getLayoutParams();
        final int leftRightMargin = ViewUtils.getDisplayPerWidthByRes(getContext(), R.integer.trans_parent_tab_margin_left_right_per);

        params.setMargins(leftRightMargin, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight() / 2, leftRightMargin, 0);
        mFeedTabView.setLayoutParams(params);
        mFeedTabView.setOnTabItemClickListener(this);
        mFeedTabView.addTabItem(getResources().getString(R.string.feed_text_follow));
        mFeedTabView.addTabItem(getResources().getString(R.string.feed_text_featured));
        mFeedTabView.addTabItem(getResources().getString(R.string.feed_text_foryou));
        mFeedTabView.setActiveTab(1);
    }

    private void initMusicRecordView() {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mMusicRecordView.getLayoutParams();
        final int bottomMargin = ViewUtils.getDisplayPerHeightByRes(getContext(), R.integer.music_record_view_margin_bottom_per);
        final int rightMargin = ViewUtils.getDisplayPerWidthByRes(getContext(), R.integer.music_record_view_margin_right_per);
        params.setMargins(0, 0, rightMargin, bottomMargin);
        mMusicRecordView.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> mMusicRecordView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate))
        );
    }

    private ArrayList<FeedRepo> getDummyFeedRepos() {
        ArrayList<FeedRepo> ret = new ArrayList<>();

        final Drawable[] drawables = {
                getResources().getDrawable(R.drawable.dummy_vodeo_0),
                getResources().getDrawable(R.drawable.dummy_vodeo_1),
                getResources().getDrawable(R.drawable.dummy_vodeo_2),
                getResources().getDrawable(R.drawable.dummy_vodeo_3),
                getResources().getDrawable(R.drawable.dummy_vodeo_4),
                getResources().getDrawable(R.drawable.dummy_vodeo_5),
                getResources().getDrawable(R.drawable.dummy_vodeo_6),
                getResources().getDrawable(R.drawable.dummy_vodeo_7),
                getResources().getDrawable(R.drawable.dummy_vodeo_8),
        };

        final String[] profile  = {
                "https://pbs.twimg.com/profile_images/565601976063647744/PP085xzu.jpeg",
                "https://pbs.twimg.com/profile_images/565601976063647744/PP085xzu.jpeg",
                "https://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/13388457_1178748805489277_22468065_n.jpg",
                "http://static.theappl.com/gallery/201512/3064187941_bbdbf0d9_89.jpg",
                "https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/e15/11358125_937267183004110_1228791496_n.jpg",
                "https://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/13388457_1178748805489277_22468065_n.jpg",
                "http://static.theappl.com/gallery/201512/3064187941_bbdbf0d9_89.jpg",
                "https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/e15/11358125_937267183004110_1228791496_n.jpg"
        };


        String VIDEO_FILE_NAME = "dummy_vodeo";
        Uri videoFile = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/" + VIDEO_FILE_NAME);

        ret.add(
                new FeedRepo(getContext(), getResources().getString(R.string.user_name_text_dummy), videoFile,
                        getResources().getDrawable(R.drawable.dummy_vodeo), "https://pbs.twimg.com/profile_images/565601976063647744/PP085xzu.jpeg",
                        getResources().getString(R.string.video_specification_dummy), true, this)
        );

        for (int i = 0; i <= 8; i++) {
            VIDEO_FILE_NAME = "dummy_vodeo_" + String.valueOf(i);
            videoFile = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/" + VIDEO_FILE_NAME);
            ret.add(
                    new FeedRepo(getContext(), getResources().getString(R.string.user_name_text_dummy), videoFile,
                            drawables[i % drawables.length], profile[i % profile.length],
                            getResources().getString(R.string.video_specification_dummy), true, this)
            );
        }
        return ret;
    }

    /**
     * call this method form recyclerView ItemCallback interface.
     * the method has args a view and a currentItemPosition.
     * using mMusicRecordView
     **/
    @Override public void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition) {
        //mFeedPresenter.recyclerViewItemActiveChanged(newActiveView, newActiveViewPosition);
        Timber.e("newActiveViewPosition %d" , newActiveViewPosition);
        final int[] mDrawables = {
                R.drawable.test_recode,
                R.drawable.test_recode1,
                R.drawable.test_recode2
        };

        Picasso.with(getContext())
                .load(mDrawables[newActiveViewPosition % 3])
                .placeholder(R.drawable.recode_placeholder)
                .transform(new CircleTransform())
                .into(mMusicRecordView);
    }

    @Override public void onTabItemClick(TabView tabView) {
        switch (tabView.getIndex()) {

            case 0:
                Timber.e(tabView.getTabText());
                break;
            case 1:
                Timber.e(tabView.getTabText());
                break;
            case 2:
                Timber.e(tabView.getTabText());
                break;
            default:
                break;
        }
    }

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            mScrollState = scrollState;
            if (scrollState == RecyclerView.SCROLL_STATE_IDLE && !mFeedRepos.isEmpty()) {

                mListItemVisibilityCalculator.onScrollStateIdle(
                        mItemsPositionGetter,
                        mLayoutManager.findFirstVisibleItemPosition(),
                        mLayoutManager.findLastVisibleItemPosition()
                );
            }
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!mFeedRepos.isEmpty()) {

                mListItemVisibilityCalculator.onScroll(
                        mItemsPositionGetter,
                        mLayoutManager.findFirstVisibleItemPosition(),
                        mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                        mScrollState);
            }
        }
    };

    /**
     * FeedView Imp
     **/
    @Override public void setMusicRecordViewImage(String imageUri) {
        Picasso.with(getContext())
                .load(imageUri)
                .placeholder(R.drawable.recode_placeholder)
                .transform(new CircleTransform())
                .into(mMusicRecordView);
    }
}



