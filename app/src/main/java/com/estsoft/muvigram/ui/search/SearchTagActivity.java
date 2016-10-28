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
import com.estsoft.muvigram.ui.profile.TagProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 25..
 */

public class SearchTagActivity extends BaseActivity {

    private static final String[] titleList = {"comedy","question","dance","snapchatstory","muservoice"};
    private static final int[] subTitleList = {123,34,436,567,12};

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_simple_list);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SearchTagAdapter adapter = new SearchTagAdapter(getSearchTagItems(), getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        params.setMargins(0,0,0,0);
        recyclerView.setLayoutParams(params);
    }

    public List<SearchListItem> getSearchTagItems()
    {
        List<SearchListItem> listItems = new ArrayList<>();
        SearchListItem[] items = new SearchListItem[5];

        for(int i=0; i<items.length; i++){
            items[i] = new SearchListItem("#"+titleList[i],subTitleList[i]+ " muvigrams");
            listItems.add(items[i]);
        }

        return listItems;
    }
}


class SearchTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG_IMAGE =
            "https://cdn1.iconfinder.com/data/icons/glyph-1-1/24/Hash_hashtag_sharp_tag_teg-512.png";

    private Context context;
    private List<SearchListItem> mSearchListItemList;

    public SearchTagAdapter(List<SearchListItem> mSearchListItemList, Context mContext) {
        this.context = mContext;
        this.mSearchListItemList = mSearchListItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int position){

        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.search_fragment_item, parent, false);
        return new ViewHolder (v);

    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        ViewHolder mHolder = (ViewHolder)holder;
        mHolder.setIsRecyclable(false);

        Picasso.with(context)
                .load(TAG_IMAGE)
                .into(mHolder.profile);
        mHolder.title.setText (mSearchListItemList.get(position).getTitle());
        mHolder.subtitle.setText(mSearchListItemList.get(position).getSubTitle());

        mHolder.layout.setOnClickListener(v -> {
            v.getContext().startActivity(new Intent(v.getContext(), TagProfileActivity.class));
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
