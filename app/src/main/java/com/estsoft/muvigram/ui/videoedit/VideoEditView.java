package com.estsoft.muvigram.ui.videoedit;

import android.content.Context;
import android.graphics.Bitmap;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by jaylim on 10/31/2016.
 */

public interface VideoEditView extends MvpView {

    Integer NO_AUDIO = -1003;
    Integer WITH_AUDIO = -1004;

    void reStartVideoView(int ms);
    void reStartAudio(int ms);
    void nextAudioEditFragment();
    void updateAudioTitle(String title);
    void updateAudioAlbum(String path);
    void nextAudioSettingFragment();
    void nextVideoFilterFragment();
    void nextVideoSpeedFragment();
    void nextAudioSelectActivity();
    void nextActivity();

}
