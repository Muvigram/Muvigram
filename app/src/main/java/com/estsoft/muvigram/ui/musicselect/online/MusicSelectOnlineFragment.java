package com.estsoft.muvigram.ui.musicselect.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.musicselect.MusicSelectView;
import com.estsoft.muvigram.ui.musicselect.injection.NestedFragment;

import javax.inject.Inject;

/**
 * Created by jaylim on 11/1/2016.
 */

public class MusicSelectOnlineFragment extends NestedFragment implements MusicSelectView.OnlineView {

    @Inject MusicSelectOnlinePresenter mPresenter;

    public static MusicSelectOnlineFragment newInstance() {
        return new MusicSelectOnlineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musicselect_online_category_grid_view, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
