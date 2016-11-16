package com.estsoft.muvigram.ui.notify;

import com.estsoft.muvigram.model.NotifyComment;
import com.estsoft.muvigram.model.NotifyFollow;
import com.estsoft.muvigram.model.NotifyLike;
import com.estsoft.muvigram.model.ProfileThumbnailRepo;
import com.estsoft.muvigram.model.UserInfoRepo;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

public interface NotifyFragmentView extends MvpView{

    void showComments(List<NotifyComment> notifyComments);
    void showCommentsEmpty();
    void showCommentsError();

    void showFollow(List<NotifyFollow> notifyFollows);
    void showFollowEmpty();
    void showFollowError();

    void showLikes(List<NotifyLike> notifyLikes);
    void showLikesEmpty();
    void showLikesError();

}
