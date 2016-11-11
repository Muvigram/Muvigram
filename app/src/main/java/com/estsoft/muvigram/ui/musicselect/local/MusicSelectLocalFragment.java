package com.estsoft.muvigram.ui.musicselect.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.base.nestedfragment.BaseNestedFragment;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
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

public class MusicSelectLocalFragment extends BaseNestedFragment implements MusicSelectView.LocalView {

    @Inject MusicSelectLocalPresenter mPresenter;
    @Inject MusicSelectLocalListAdapter mMusicsAdapter;

    @BindView(R.id.musicselect_local_list_recycler_view) RecyclerView mRecyclerView;

    public static MusicSelectLocalFragment newInstance() {
        return new MusicSelectLocalFragment();
    }

    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

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
        mPresenter.attachView(this);
    }

    @Override
    public void onStart() {
        mRecyclerView.setAdapter(mMusicsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter.loadMusics();
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showMusics(List<Music> musics) {
        mMusicsAdapter.setMusics(musics);
        mMusicsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMusicsEmpty() {
        mMusicsAdapter.setMusics(Collections.emptyList());
        mMusicsAdapter.notifyDataSetChanged();
        Toast.makeText(
                getActivity(),
                R.string.musicselect_local_list_empty_musics,
                Toast.LENGTH_LONG
        ).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(
                getActivity(),
                getString(R.string.musicselect_local_list_error_loading)
        ).show();
    }
}
