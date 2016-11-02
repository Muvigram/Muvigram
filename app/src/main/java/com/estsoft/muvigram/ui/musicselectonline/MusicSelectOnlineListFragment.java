package com.estsoft.muvigram.ui.musicselectonline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;
import com.estsoft.muvigram.ui.musicselect.local.MusicSelectLocalListAdapter;
import com.estsoft.muvigram.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jaylim on 11/2/2016.
 */

public class MusicSelectOnlineListFragment extends BaseSingleFragment
        implements MusicSelectOnlineListView {

    private Long mCategoryId;
    private static final String ARG_CATEGORY_ID
            = "category_id";

    @Inject MusicSelectOnlineListPresenter mPresenter;
    @Inject MusicSelectOnlineListAdapter mMusicsAdapter;

    @BindView(R.id.musicselect_online_list_recycler_view) RecyclerView mRecyclerView;

    private Unbinder mUnbinder;


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicselect_online_list_recycler_view, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    /* View Logic */

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSingleFragmentComponent().inject(this);
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
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
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

    public static MusicSelectOnlineListFragment newInstance(long categoryId) {
        Bundle args = new Bundle();
        args.putLong(ARG_CATEGORY_ID, categoryId);

        MusicSelectOnlineListFragment musicSelectOnlineListFragment
                = new MusicSelectOnlineListFragment();
        musicSelectOnlineListFragment.setArguments(args);

        return musicSelectOnlineListFragment;
    }
}
