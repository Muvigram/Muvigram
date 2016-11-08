package com.estsoft.muvigram.ui.feed;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.estsoft.muvigram.customview.viewpager.PreCachingLayoutManager;
import com.estsoft.muvigram.customview.viewpager.RecyclerViewPager;
import com.estsoft.muvigram.model.FeedRepo;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.estsoft.muvigram.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by jang gong ui on 2016. 10. 17..
 */

public class FeedFragment extends Fragment implements TransParentTabView.OnTabItemClickListener, FeedRepo.ItemCallback {


    @BindView(R.id.trnasparent_tab) TransParentTabView mFeedTabView;
    @BindView(R.id.pager) RecyclerViewPager mRecyclerViewPager;
    @BindView(R.id.musicRecordView) MusicRecordView mMusicRecordView;

    private PreCachingLayoutManager mLayoutManager;
    private ListItemsVisibilityCalculator mListItemVisibilityCalculator;
    private ItemsPositionGetter mItemsPositionGetter;
    private ArrayList<FeedRepo> mFeedRepos;
    private FeedAdapter mFeedAdapter;
    private int mScrollState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.e("onCreateView");

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        initViewPager();
        initTransParentTab();
        initMusicRecordView();

        return view;
    }


    @Override public void onResume() {
        super.onResume();
        if (!mFeedRepos.isEmpty()) {
            // need to call this method from list view handler in order to have filled list
            mRecyclerViewPager.post(() -> mListItemVisibilityCalculator.onScrollStateIdle(
                    mItemsPositionGetter,
                    mLayoutManager.findFirstVisibleItemPosition(),
                    mLayoutManager.findLastVisibleItemPosition()));
        }

        Picasso.with(getContext())
                .load(R.drawable.test_recode)
                .transform(new CircleTransform())
                .into(mMusicRecordView);

    }

    private void initViewPager() {
        mFeedRepos = getDummyFeedRepos();
        mLayoutManager = new PreCachingLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setExtraLayoutSpace(ViewUtils.getDisplayPerHeight(getContext(), 99));
        mListItemVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mFeedRepos);
        mFeedAdapter = new FeedAdapter(mFeedRepos);
        mRecyclerViewPager.setSinglePageFling(true);
        mRecyclerViewPager.setLayoutManager(mLayoutManager);
        mRecyclerViewPager.setHasFixedSize(true);
        mRecyclerViewPager.setLongClickable(true);
        mRecyclerViewPager.setAdapter(mFeedAdapter);
        mRecyclerViewPager.setItemViewCacheSize(10);


        mRecyclerViewPager.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE && !mFeedRepos.isEmpty()) {

                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition()
                    );
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mFeedRepos.isEmpty()) {

                    mListItemVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);
                }
            }
        });

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

        Drawable[] drawables = {
                getResources().getDrawable(R.drawable.test_intro_thumbnai),
                getResources().getDrawable(R.drawable.test_intro_thumbnai),
                getResources().getDrawable(R.drawable.test_intro_thumbnai1),
                getResources().getDrawable(R.drawable.test_intro_thumbnai2)
        };

        for (int i = 1; i <= 3; i++) {
            final String VIDEO_FILE_NAME = "test_intro_video" + String.valueOf(i);
            final Uri videoFile = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/" + VIDEO_FILE_NAME);


            for(int j = 0; j < 20; j++) {
                ret.add(
                        new FeedRepo(getContext(), getResources().getString(R.string.user_name_text_dummy), videoFile,
                                drawables[i], "https://pbs.twimg.com/profile_images/565601976063647744/PP085xzu.jpeg",
                                getResources().getString(R.string.video_specification_dummy), true, this)
                );
            }
        }
        return ret;
    }


    @Override public void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition) {
        Timber.e("newActiveViewPosition = %d", newActiveViewPosition);

        int[] dummyImages = {
                R.drawable.test_recode,
                R.drawable.test_recode1,
                R.drawable.test_recode2
        };

        Picasso.with(getContext())
                .load(dummyImages[newActiveViewPosition % dummyImages.length])
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
}
