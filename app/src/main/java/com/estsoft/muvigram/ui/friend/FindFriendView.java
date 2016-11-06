package com.estsoft.muvigram.ui.friend;

import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by jaylim on 11/5/2016.
 */

public interface FindFriendView extends MvpView {

    /* Load Friends */
    void showFriends(List<Friend> friends);

    void showFriendsEmpty();

    void showError();

}
