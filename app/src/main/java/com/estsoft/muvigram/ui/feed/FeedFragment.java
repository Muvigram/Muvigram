package com.estsoft.muvigram.ui.feed;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.MusicRecordView;
import com.estsoft.muvigram.customview.viewpager.RecyclerViewPager;
import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.estsoft.muvigram.util.DialogFactory;
import com.estsoft.muvigram.util.ViewUtils;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by jang gong ui on 2016. 10. 17..
 */

public class FeedFragment extends Fragment implements FeedView, FeedItem.ItemCallback {

    private final List<FeedItem> mFeedItems = new ArrayList<>();
    private final ListItemsVisibilityCalculator mListItemVisibilityCalculator =
            new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mFeedItems);
    private ProgressDialog mFeedLoadDialog;
    private LinearLayoutManager mLayoutManager;
    private ItemsPositionGetter mItemsPositionGetter;
    private int mScrollState;

    @BindView(R.id.pager) RecyclerViewPager mRecyclerViewPager;
    @BindView(R.id.musicRecordView) MusicRecordView mMusicRecordView;
    @Inject FeedPresenter mFeedPresenter;
    @Inject FeedAdapter mFeedAdapter;

    // TODO: dagger inject and ButterKnife, Presenter init, view initialization
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_feed, container, false);
        // dagger
        final ParentFragmentComponent mParentFragmentComponent =
                ((HomeActivity) getActivity()).getSingleFragmentActivityComponent(this);
        mParentFragmentComponent.inject(this);
        // ButterKnife
        ButterKnife.bind(this, view);
        // presenter
        mFeedPresenter.attachView(this);
        // view init
        initRecyclerViewPager();
        initMusicRecordView();
        return view;
    }

    // TODO: set adapter and call recyclerViewPager Item from presenter.
    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFeedAdapter.setFeedItems(mFeedItems);
        mFeedPresenter.loadFeedRepos();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        mFeedPresenter.detachView();
    }

    // view initialization
    private void initRecyclerViewPager() {
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewPager.setAdapter(mFeedAdapter);
        mRecyclerViewPager.setSinglePageFling(true);
        mRecyclerViewPager.setLayoutManager(mLayoutManager);
        mRecyclerViewPager.setHasFixedSize(true);
        mRecyclerViewPager.setLongClickable(true);
        mRecyclerViewPager.setItemViewCacheSize(10);
        mRecyclerViewPager.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE && !mFeedItems.isEmpty()) {
                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition()
                    );
                }
            }

            @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mFeedItems.isEmpty()) {
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

    private void initMusicRecordView() {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                mMusicRecordView.getLayoutParams();
        final int bottomMargin = ViewUtils.getDisplayPerHeightByRes(getContext(), R.integer.music_record_view_margin_bottom_per);
        final int rightMargin = ViewUtils.getDisplayPerWidthByRes(getContext(),
                R.integer.music_record_view_margin_right_per);
        params.setMargins(0, 0, rightMargin, bottomMargin);
        mMusicRecordView.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                        mMusicRecordView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate))
        );
    }

    /**
     * call this method form recyclerView ItemCallback interface.
     * the method has args a view and a currentItemPosition.
     * using mMusicRecordView
     * ->>>> 수정 필요함 <<<<-
     **/
    @Override public void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition) {
        mFeedPresenter.recyclerViewItemActiveChanged(mFeedItems.get(newActiveViewPosition));
    }

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

    @Override public void showFeedList(List<FeedItem> feedItems) {
        mFeedAdapter.addFeedItems(feedItems);
        mFeedAdapter.notifyDataSetChanged();
        // the first item start signal for a recyclerView .
        if (!mFeedItems.isEmpty()) {
            mRecyclerViewPager.post(() -> mListItemVisibilityCalculator.onScrollStateIdle(
                    mItemsPositionGetter,
                    mLayoutManager.findFirstVisibleItemPosition(),
                    mLayoutManager.findLastVisibleItemPosition()));
        }
    }

    @Override public void showFeedLoadDialog(int resourceId) {
        if (mFeedLoadDialog == null) {
            mFeedLoadDialog = DialogFactory.createProgressDialog(getContext(), resourceId);
        }
        mFeedLoadDialog.show();
    }

    @Override public void hideFeedLoadDialog() {
        if (mFeedLoadDialog != null) {
            mFeedLoadDialog.hide();
        }
    }
}



