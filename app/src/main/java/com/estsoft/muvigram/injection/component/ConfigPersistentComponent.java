package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.injection.ConfigPersistent;
import com.estsoft.muvigram.injection.module.PlainActivityModule;
import com.estsoft.muvigram.injection.module.SingleFragmentActivityModule;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;

import dagger.Component;

/**
 * In android's perspective :
 *   1. It is only for the persistency of the component and it's 'dependency object graph'
 *      from a configuration change.
 *   2. It will not contain any additional functionality.
 *
 * In dagger's perspective :
 *   1. make sure the persistency of the {@link ConfigPersistentComponent}
 *      from the configuration change of some activity which extends {@link BaseActivity}.
 *
 * In DI's perspective :
 *   1. Implicit dependency objects
 *     1) Context context() from {@link ApplicationComponent}
 *     2) Application application() from {@link ApplicationComponent}
 *     3) {@link DataManager} from {@link ApplicationComponent}
 *
 *   2. Explicit dependency objects
 *     1) TODO
 *
 * Created  by gangGongUi on 2016. 10. 9.
 * Modified by Jay Lim    on 2016. 10. 31.
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    /* Subcomponent */
    PlainActivityComponent plus(PlainActivityModule plainActivityModule);
    SingleFragmentActivityComponent plus(SingleFragmentActivityModule singleFragmentActivityModule);

    /* Dependencies extended by constructor injections */

    /* Dependencies provided from modules */

    /* Field injection */

}
