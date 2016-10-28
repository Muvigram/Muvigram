package com.estsoft.muvigram.ui.search;

import android.content.Context;
import android.content.Intent;
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
import com.estsoft.muvigram.ui.base.BaseActivity;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.estsoft.muvigram.ui.profile.SoundProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 25..
 */

public class SearchSoundActivity extends BaseActivity {

    private static final String[] titleList = {"Closer ft Halsey","Cold Water","Perfect Illusion"};
    private static final String[] subTitleList = {"The Chainsomkers","Major Lazer","Lady Gaga"};
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_simple_list);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SearchSoundAdapter adapter = new SearchSoundAdapter(getSearchSoundItems(), getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        params.setMargins(0,0,0,0);
        recyclerView.setLayoutParams(params);
    }

    public List<SearchListItem> getSearchSoundItems()
    {
        List<SearchListItem> listItems = new ArrayList<>();
        SearchListItem[] items = new SearchListItem[3];

        for(int i=0; i<items.length; i++){
            items[i] = new SearchListItem(titleList[i],subTitleList[i]);
            listItems.add(items[i]);
        }

        return listItems;
    }
}

class SearchSoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String PROFILE_IMAGE =
            "https://scontent.xx.fbcdn.net/v/t1.0-9/12011354_171091463233969_4930354003965117617_n.jpg?oh=5d04533c62af8fed3eeab63f36df659a&oe=589FE419";

    private Context context;
    private List<SearchListItem> mSearchListItemList;

    public SearchSoundAdapter(List<SearchListItem> mSearchListItemList, Context mContext) {
        this.context = mContext;
        this.mSearchListItemList = mSearchListItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int position){
        View v = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.search_fragment_item, parent, false);
        return new ViewHolder (v);
    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {

        ViewHolder mHolder = (ViewHolder)holder;
        mHolder.setIsRecyclable(false);

        Picasso.with(context)
                .load(PROFILE_IMAGE)
                .into(mHolder.profile);
        mHolder.title.setText (mSearchListItemList.get(position).getTitle());
        mHolder.subtitle.setText(mSearchListItemList.get(position).getSubTitle());
        mHolder.layout.setOnClickListener(v -> {
            v.getContext().startActivity(new Intent(v.getContext(), SoundProfileActivity.class));
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


}
