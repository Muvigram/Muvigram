package com.estsoft.muvigram.ui.musicselect.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.nestedfragment.BaseParentFragment;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.local.MusicSelectLocalFragment;
import com.estsoft.muvigram.ui.musicselect.online.MusicSelectOnlineFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *
 * Created by jaylim on 10/31/2016.
 */

public class MusicSelectFragment extends BaseParentFragment implements MusicSelectView.ParentView {

    private final static int PAGE_ONLINE_LIBRARY = 0;
    private final static int PAGE_LOCAL_LIBRARY = 1;

    @Inject MusicSelectPresenter mPresenter;
    @Inject MusicSelectPagerAdapter mPagerAdapter;

    @BindView(R.id.musicselect_view_pager) ViewPager mViewPager;

    @BindView(R.id.musicselect_tab_bar_online_button) Button mOnlineButton;
    @BindView(R.id.musicselect_tab_bar_local_button) Button mLocalButton;
    @BindView(R.id.musicselect_tab_bar_back_button) ImageButton mBackButton;

    @OnClick(R.id.musicselect_tab_bar_online_button)
    public void showOnlineLibrary() {
        syncPage(PAGE_ONLINE_LIBRARY);
    }

    @OnClick(R.id.musicselect_tab_bar_local_button)
    public void showLocalLibrary() {
        syncPage(PAGE_LOCAL_LIBRARY);
    }

    @OnClick(R.id.musicselect_tab_bar_back_button)
    public void finishActivity() {
        getActivity().onBackPressed();
    }

    private Unbinder mUnbinder;

    public static MusicSelectFragment newInstance() {
        return new MusicSelectFragment();
    }

    // ButterKnife view binding here ...

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicselect, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    // Dagger injection here ...

    List<Fragment> mFragments = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getParentFragmentComponent().inject(this);
        mPresenter.attachView(this);

        mFragments.add(MusicSelectOnlineFragment.newInstance());
        mFragments.add(MusicSelectLocalFragment.newInstance());

        mFragments = Collections.unmodifiableList(mFragments);

        mPagerAdapter.setFragments(mFragments);

        mViewPager.setAdapter(mPagerAdapter);

        syncPage(PAGE_ONLINE_LIBRARY);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                syncPage(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void syncPage(int position) {
        mViewPager.setCurrentItem(position);
        switch (position) {
            case PAGE_ONLINE_LIBRARY :
                mOnlineButton.setBackgroundResource(R.color.white);
                mLocalButton.setBackgroundResource(R.color.gray);
                break;
            case PAGE_LOCAL_LIBRARY :
                mLocalButton.setBackgroundResource(R.color.white);
                mOnlineButton.setBackgroundResource(R.color.gray);
                break;
        }

    }


}
