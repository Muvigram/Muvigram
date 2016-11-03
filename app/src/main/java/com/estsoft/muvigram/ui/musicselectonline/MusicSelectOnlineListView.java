package com.estsoft.muvigram.ui.musicselectonline;

import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by jaylim on 11/2/2016.
 */

public interface MusicSelectOnlineListView extends MvpView {

    void showMusics(List<Music> musics);

    void showMusicsEmpty();

    void showError();
}
