package com.estsoft.muvigram.ui.musicselect;

import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface MusicSelectView {

    interface ParentView extends MvpView {
        /* View logic here */

    }

    interface OnlineView extends MvpView {

        void showCategories(List<Category> categories);

        void showCategoriesEmpty();

        void showError();
    }

    interface LocalView extends MvpView {

        void showMusics(List<Music> musics);

        void showMusicsEmpty();

        void showError();
    }


}
