package com.estsoft.muvigram.ui.videoedit;

import com.estsoft.muvigram.ui.base.MvpView;

/**
 * Created by estsoft on 2016-11-04.
 */

public interface AudioCutView extends MvpView {

    void updateDisplay( int playedPositionMs, int audioOffsetMs );
    void restartVideoAt( int ms );
    void restartAudioAt( int ms );
    void providingMediaCurrentPosition();
    void providingPixelToMillis( int pixel );
    void backToVideoEditFragment();
}
