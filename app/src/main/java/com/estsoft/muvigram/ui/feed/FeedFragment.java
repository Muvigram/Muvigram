package com.estsoft.muvigram.ui.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.estsoft.muvigram.MuviGramApplication;
import com.estsoft.muvigram.R;
import com.gonigoni.transparenttabview.TabView;
import com.gonigoni.transparenttabview.TransParentTabView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by jang gong ui on 2016. 10. 17..
 */

public class FeedFragment extends Fragment implements TransParentTabView.OnTabItemClickListener {


    @BindView(R.id.trnasparent_tab) TransParentTabView mFeedTabView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        initTransParentTab();
        return view;
    }


    private void initTransParentTab() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mFeedTabView.getLayoutParams();
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFeedTabView.getLayoutParams();
        params.setMargins(lp.leftMargin, ((MuviGramApplication) getActivity().getApplication()).getStatusBarHeight() / 2, lp.rightMargin, 0);
        mFeedTabView.setLayoutParams(params);
        mFeedTabView.setOnTabItemClickListener(this);
        mFeedTabView.addTabItem(getResources().getString(R.string.feed_text_follow));
        mFeedTabView.addTabItem(getResources().getString(R.string.feed_text_featured));
        mFeedTabView.addTabItem(getResources().getString(R.string.feed_text_foryou));
        //mFeedTabView.setActiveTab(1);
    }

    @Override
    public void onTabItemClick(TabView tabView) {
        switch (tabView.getIndex()) {

            case 0:
                Timber.e(tabView.getTabText());
                break;
            case 1:
                Timber.e(tabView.getTabText());
                break;

            case 2:
                Timber.e(tabView.getTabText());
                break;
            default:
                break;
        }
    }
}
