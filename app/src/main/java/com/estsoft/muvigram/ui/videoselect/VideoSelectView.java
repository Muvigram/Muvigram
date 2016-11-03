package com.estsoft.muvigram.ui.videoselect;

import android.graphics.Bitmap;

import com.estsoft.muvigram.model.VideoMetaData;
import com.estsoft.muvigram.ui.base.MvpView;

import java.util.List;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface VideoSelectView extends MvpView {

    /* View logic interface here ... */

    void updateThumbnails( VideoMetaData metaData );

    void enableProgress();

    void disableProgress();

    void nextActivity(String path);

    void showError();

}
