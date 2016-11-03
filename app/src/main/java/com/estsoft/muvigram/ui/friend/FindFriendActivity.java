package com.estsoft.muvigram.ui.friend;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 13..
 */

public class FindFriendActivity extends BasePlainActivity {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @OnClick(R.id.back_button) void clickBack(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        MayKnowItemAdapter adapter = new MayKnowItemAdapter(getSearchPeopleItems(), this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        mRecyclerView.setNestedScrollingEnabled(false);

        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
        ViewGroup.LayoutParams params=mRecyclerView.getLayoutParams();
        params.height=getSearchPeopleItems().size() * (int)pixels;
        mRecyclerView.setLayoutParams(params);
    }


    public List<MayKnowListItem> getSearchPeopleItems()
    {
        List<MayKnowListItem> listItems = new ArrayList<>();
        MayKnowListItem[] items = new MayKnowListItem[6];

        for(int i=0; i<items.length; i++){
            items[i] = new MayKnowListItem("name#"+i,"id id id");
            listItems.add(items[i]);
        }

        return listItems;
    }
}
