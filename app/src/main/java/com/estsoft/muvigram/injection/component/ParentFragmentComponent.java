package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.module.NestedFragmentModule;
import com.estsoft.muvigram.injection.module.ParentFragmentModule;
import com.estsoft.muvigram.ui.musicselect.pager.MusicSelectFragment;

import dagger.Subcomponent;

/**
 * Created by jaylim on 11/2/2016.
 */


@PerParentFragment
@Subcomponent(modules=ParentFragmentModule.class)
public interface ParentFragmentComponent {
    /* Subcomponent */
    NestedFragmentComponent plus(NestedFragmentModule nestedFragmentModule);

    /* Dependencies from provider */
    // TODO ...

    /* Dependencies from constructor injection */
    // TODO ...

    /* Field injection */
    void inject(MusicSelectFragment musicSelectFragment);

}
