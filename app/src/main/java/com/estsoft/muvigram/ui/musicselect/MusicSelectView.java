package com.estsoft.muvigram.ui.musicselect;

import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface MusicSelectView {

    interface ParentView extends MvpView {
        /* View logic here */


        // public void showError();

        // public void showLocalMusicList(List<Music> musicList);

        // public void showEmptyLocalMusicList();

    }


    interface OnlineView extends MvpView {

    }

    interface LocalView extends MvpView {

        void showError();

        void showMusics(List<Music> musicList);

        void showMusicsEmpty();
    }


}
