package com.estsoft.muvigram.ui.musicselectonline;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

/**
 * Created by jaylim on 11/2/2016.
 */

public class MusicSelectOnlineListActivitiy extends BaseSingleFragmentActivity {

    private static final String EXTRA_CATEGORY_ID
            = "com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity.category_id";

    public static Intent newIntent(Context packageContext, Long CategoryId) {
        Intent intent = new Intent(packageContext, MusicSelectOnlineListActivitiy.class);
        intent.putExtra(EXTRA_CATEGORY_ID, CategoryId);
        return intent;
    }

    @Override
    protected Fragment createDefaultFragment() {
        Long categoryId = getIntent().getLongExtra(EXTRA_CATEGORY_ID, -1);
        // TODO Exception handling if categoryId == -1
        return MusicSelectOnlineListFragment.newInstance(categoryId);
    }
}
