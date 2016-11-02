package com.estsoft.muvigram.ui.musicselect.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.injection.NestedFragment;
import com.estsoft.muvigram.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jaylim on 11/1/2016.
 */

public class MusicSelectOnlineFragment extends NestedFragment implements MusicSelectView.OnlineView {

    @Inject MusicSelectOnlinePresenter mPresenter;
    @Inject
    MusicSelectOnlineCategoryAdapter mCategoryAdapter;

    @BindView(R.id.musicselect_online_category_recycler_view) RecyclerView mRecyclerView;



    public static MusicSelectOnlineFragment newInstance() {
        return new MusicSelectOnlineFragment();
    }

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicselect_online_category_grid_view, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNestedFragmentComponent().inject(this);
        mPresenter.attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mCategoryAdapter);
        mPresenter.loadCategories();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /* Reactive view logic */

    @Override
    public void showCategories(List<Category> categories) {
        mCategoryAdapter.setCategories(categories);
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCategoriesEmpty() {
        mCategoryAdapter.setCategories(Collections.emptyList());
        mCategoryAdapter.notifyDataSetChanged();
        Toast.makeText(
                getActivity(),
                R.string.musicselect_online_category_empty_musics,
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(
                getActivity(),
                getString(R.string.musicselect_online_category_error_loading)
        ).show();
    }
}
