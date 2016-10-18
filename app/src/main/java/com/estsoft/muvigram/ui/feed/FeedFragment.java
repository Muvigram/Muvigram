package com.estsoft.muvigram.ui.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.estsoft.muvigram.MuviGramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.FeedTabView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by jang gong ui on 2016. 10. 17..
 */

public class FeedFragment extends Fragment implements FeedTabView.onFeedTabItemClickListener {


    @BindView(R.id.feedTabView) FeedTabView mFeedTabView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);




        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFeedTabView.getLayoutParams();
        params.setMargins(0, ((MuviGramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mFeedTabView.setLayoutParams(params);
        mFeedTabView.setOnFeedTabItemClickListener(this);
        mFeedTabView.setActiveItem(1);

        return view;
    }


    @Override
    public void onItemClick(FeedTabView.BarItem barItem)
    {
        switch (barItem.getIndex()) {

            case 0:

                break;
            case 1:

                break;

            case 2:

                break;
            default:
                break;
        }
    }
}
