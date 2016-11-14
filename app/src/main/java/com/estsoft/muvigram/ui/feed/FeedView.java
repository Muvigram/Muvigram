package com.estsoft.muvigram.ui.feed;

import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by gangGongUi on 2016. 11. 8..
 */
public interface FeedView extends MvpView {

    void setMusicRecordViewImage(String imageUri);
    void showFeedList(List<FeedItem> feedItems);
    void showFeedLoadDialog(int resourceId);
    void hideFeedLoadDialog();
}
