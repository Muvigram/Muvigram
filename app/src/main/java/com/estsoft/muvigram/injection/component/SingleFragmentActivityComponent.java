package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.injection.PerSingleFragmentActivity;
import com.estsoft.muvigram.injection.module.SingleFragmentModule;
import com.estsoft.muvigram.injection.module.SingleFragmentActivityModule;

import dagger.Subcomponent;

/**
 * Created by jaylim on 10/31/2016.
 */

@PerSingleFragmentActivity
@Subcomponent(modules = SingleFragmentActivityModule.class)
public interface SingleFragmentActivityComponent {
    /* Subcomponent */
    SingleFragmentComponent plus(SingleFragmentModule singleFragmentModule);

    /* Dependencies extended by constructor injections */
    // TODO ...

    /* Dependencies provided from modules */
    // TODO ...

    /* Field injection */
    //
}
