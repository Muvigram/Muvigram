package com.estsoft.muvigram.ui.base.fragment;

import android.support.v4.app.Fragment;

import com.estsoft.muvigram.injection.component.SingleFragmentActivityComponent;
import com.estsoft.muvigram.ui.base.nestedfragment.BaseParentFragment;

/**
 * Created by jaylim on 11/2/2016.
 */

public class BaseFragment extends Fragment {

    /** Do not use in any fragment but only in {@link BaseParentFragment} */
    protected SingleFragmentActivityComponent getSingleFragmentActivityComponent() {
        return BaseSingleFragmentActivity.get(this).getSingleFragmentActivityComponent();
    }
}
