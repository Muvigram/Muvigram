package com.estsoft.muvigram.ui.search;

import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

public interface SearchBarView extends MvpView{

    void showUsers(List<Friend> users);
    void showUsersEmpty();
    void showUsersError();

    void showTags(List<Tag> tags);
    void showTagsEmpty();
    void showTagsError();

    void showMusics(List<Music> musics);
    void showMusicsEmpty();
    void showMusicsError();
}
