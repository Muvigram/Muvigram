package com.estsoft.muvigram.ui.musicselect.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.injection.NestedFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jaylim on 11/1/2016.
 */

public class MusicSelectLocalFragment extends NestedFragment implements MusicSelectView.LocalView {

    @Inject MusicSelectLocalPresenter mPresenter;

    @BindView(R.id.musicselect_local_list_recycler_view)
    RecyclerView mRecyclerView;

    public static MusicSelectLocalFragment newInstance() {
        return new MusicSelectLocalFragment();
    }

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicselect_local_list_recycler_view, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNestedFragmentComponent().inject(this);
    }

    @Override
    public void onStart() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.getMusics();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    @Override
    public void showMusics(List<Music> musicList) {

    }

    @Override
    public void showMusicsEmpty() {

    }

    @Override
    public void showError() {

    }
}
