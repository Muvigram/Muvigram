package com.estsoft.muvigram.ui.profile;

import com.estsoft.muvigram.model.ProfileThumbnailRepo;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.model.UserInfoRepo;
import com.estsoft.muvigram.ui.base.MvpView;
import com.estsoft.muvigram.ui.search.SearchHeaderVideoItem;

import java.util.List;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

public interface ProfileFragmentView extends MvpView{

    void showUserInfo(UserInfoRepo userInfo);
    void showUserInfoEmpty();
    void showUserInfoError();

    void showThumbnail(List<ProfileThumbnailRepo> thumbnailRepo);
    void showThumbnailEmpty();
    void showThumbnailError();
}
