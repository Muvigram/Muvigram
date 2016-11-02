package com.estsoft.muvigram.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 25..
 */

public class SearchPeopleActivity extends BasePlainActivity {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_simple_list);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SearchPeopleAdapter adapter = new SearchPeopleAdapter(getSearchPeopleItems(), getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        params.setMargins(0,0,0,0);
        recyclerView.setLayoutParams(params);
    }

    public List<SearchListItem> getSearchPeopleItems()
    {
        List<SearchListItem> listItems = new ArrayList<>();
        SearchListItem[] items = new SearchListItem[6];

        for(int i=0; i<items.length; i++){
            items[i] = new SearchListItem();
            listItems.add(items[i]);
        }

        return listItems;
    }
}

class SearchPeopleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String PROFILE_IMAGE = "https://scontent.xx.fbcdn.net/v/t1.0-9/12011354_171091463233969_4930354003965117617_n.jpg?oh=5d04533c62af8fed3eeab63f36df659a&oe=589FE419";
    private static final String VIDEO_THUMBNAILS = "https://pixabay.com/static/uploads/photo/2016/01/05/17/51/dog-1123016_960_720.jpg";

    private Context context;
    private List<SearchListItem> mSearchListItemList;
    private ArrayList<SearchListItem> mSearchResultList;

    public SearchPeopleAdapter(List<SearchListItem> mSearchListItemList, Context mContext) {
        this.context = mContext;
        this.mSearchListItemList = mSearchListItemList;

        this.mSearchResultList = new ArrayList<SearchListItem>();
        mSearchResultList.addAll(mSearchListItemList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int position){

        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.search_fragment_item, parent, false);
        return new ViewHolder (v);

    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {

        ViewHolder mHolder = (ViewHolder)holder;
        mHolder.setIsRecyclable(false);

        Picasso.with(context)
                .load(PROFILE_IMAGE)
                .transform(new CircleTransform()).into(mHolder.profile);

        mHolder.title.setText ("people title");
        mHolder.subtitle.setText("people subtitle");

        mHolder.layout.setOnClickListener(v -> {

        });
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType (int position) {
        return position;
    }


    @Override
    public int getItemCount () {
        return mSearchListItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_image)
        ImageView profile;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle) TextView subtitle;
        @BindView(R.id.search_item_layout) LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mSearchListItemList.clear();
        if (charText.length() == 0) {
            mSearchListItemList.addAll(mSearchResultList);
        } else {
            for (SearchListItem item : mSearchResultList) {
                String title = item.getTitle();
                if (title.toLowerCase().contains(charText)) {
                    mSearchListItemList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


}
