package com.estsoft.muvigram.ui.search;

import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

public interface TrendingTagsView extends MvpView{

    void showTags(List<Tag> tags);
    void showTagsEmpty();
    void showError();
}
