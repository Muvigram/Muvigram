package com.estsoft.muvigram.ui.musicselect.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.injection.NestedFragment;
import com.estsoft.muvigram.ui.musicselect.local.MusicSelectLocalFragment;
import com.estsoft.muvigram.ui.musicselect.online.MusicSelectOnlineFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jaylim on 10/31/2016.
 */

public class MusicSelectFragment extends BaseSingleFragment implements MusicSelectView.ParentView {

    @Inject MusicSelectPresenter mPresenter;

    @BindView(R.id.musicselect_view_pager) ViewPager mViewPager;


    private Unbinder mUnbinder;

    public static MusicSelectFragment newInstance() {
        return new MusicSelectFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Butterknife view binding here ...

    @Nullable
    @Override
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
        getSingleFragmentComponent().inject(this);

        mFragments.add(MusicSelectOnlineFragment.newInstance());
        mFragments.add(MusicSelectLocalFragment.newInstance());

        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        mViewPager.setCurrentItem(0);

        mPresenter.attachView(this);
    }

    // View logic(?) here ...

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /** This method is only for {@link NestedFragment} or its child fragment. */
    public static MusicSelectFragment get(NestedFragment nestedFragment) {
        return (MusicSelectFragment) nestedFragment.getParentFragment();
    }


}
