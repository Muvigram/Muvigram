package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.injection.PerNestedFragment;
import com.estsoft.muvigram.injection.module.NestedFragmentModule;
import com.estsoft.muvigram.ui.musicselect.local.MusicSelectLocalFragment;
import com.estsoft.muvigram.ui.musicselect.online.MusicSelectOnlineFragment;

import dagger.Subcomponent;

/**
 * Created by jaylim on 11/2/2016.
 */


@PerNestedFragment
@Subcomponent(modules= NestedFragmentModule.class)
public interface NestedFragmentComponent {
    /* Subcomponent */
    // TODO ...

    /* Dependencies from provider */
    // TODO ...

    /* Dependencies from constructor injection */
    // TODO ...

    /* Field injection */
    void inject(MusicSelectLocalFragment musicSelectLocalFragment);
    void inject(MusicSelectOnlineFragment musicSelectOnlineFragment);
}
