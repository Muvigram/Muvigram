package com.estsoft.muvigram.ui.search;

import android.content.Context;
import android.icu.util.Freezable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 28..
 */

@PerPlainActivity
public class SearchItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String PROFILE_IMAGE = "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg";
    private static final String TAG_IMAGE = "https://cdn1.iconfinder.com/data/icons/glyph-1-1/24/Hash_hashtag_sharp_tag_teg-512.png";

    private final static int PEOPLE_INDEX = 0;
    private final static int TAGS_INDEX = 1;
    private final static int SOUNDS_INDEX = 2;

    private Context context;
    private int index;

//    private List<SearchListItem> mSearchListItemList;
//    private ArrayList<SearchListItem> mSearchResultList;

    private List<Friend> mUserList;
    private ArrayList<Friend> mUserResultList;

    private List<Tag> mTagList;
    private ArrayList<Tag> mTagResultList;

    private List<Music> mMusicList;
    private ArrayList<Music> mMusicResultList;

    @Inject
    public SearchItemAdapter(@ActivityContext Context context) {
        this.mUserList = new ArrayList<>();
        this.mTagList = new ArrayList<>();
        this.mMusicList = new ArrayList<>();
        this.context = context;
    }

    public void setUsers(List<Friend> users){
        mUserList = users;
        this.mUserResultList = new ArrayList<Friend>();
        mUserResultList.addAll(mUserList);
        index = PEOPLE_INDEX;
    }

    public void setTags(List<Tag> tags){
        mTagList = tags;
        this.mTagResultList = new ArrayList<Tag>();
        mTagResultList.addAll(mTagList);
        index = TAGS_INDEX;
    }

    public void setMusics(List<Music> musics){
        mMusicList = musics;
        this.mMusicResultList = new ArrayList<Music>();
        mMusicResultList.addAll(mMusicList);
        index = SOUNDS_INDEX;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int position){

        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.search_fragment_item, parent, false);
        return new SearchItemAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {

        ViewHolder mHolder = (ViewHolder)holder;
        mHolder.setIsRecyclable(false);

        if(index == PEOPLE_INDEX ) {
            float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
            int pixel = (int)pixels;
            mHolder.profile.setPadding(pixel,pixel,pixel,pixel);

            Picasso.with(context)
                    .load(mUserList.get(position).profile())
                    .transform(new CircleTransform()).into(mHolder.profile);

            mHolder.title.setText(mUserList.get(position).name());
            mHolder.subtitle.setText(mUserList.get(position).id());

        }else if(index == TAGS_INDEX ){
            Picasso.with(context)
                    .load(TAG_IMAGE)
                    .into(mHolder.profile);

            mHolder.title.setText(mTagList.get(position).tagName());
            mHolder.subtitle.setText(mTagList.get(position).numOfContents()+"");

        }else if(index == SOUNDS_INDEX ){
            Picasso.with(context)
                    .load(mMusicList.get(position).albumCover())
                    .into(mHolder.profile);

            mHolder.title.setText(mMusicList.get(position).title());
            mHolder.subtitle.setText(mMusicList.get(position).artist());
        }

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
        if(index == PEOPLE_INDEX){
            return mUserList.size();
        }else if(index == TAGS_INDEX){
            return  mTagList.size();
        }else if(index == SOUNDS_INDEX){
            return mMusicList.size();
        }

        return -1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_image)
        ImageView profile;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle) TextView subtitle;
        @BindView(R.id.search_item_layout)
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        if(index == PEOPLE_INDEX) {
            mUserList.clear();
            if (charText.length() == 0) {
                mUserList.addAll(mUserResultList);
            } else {
                for (Friend item : mUserResultList) {
                    String title = item.name();
                    if (title.toLowerCase().contains(charText)) {
                        mUserList.add(item);
                    }
                }
            }
        } else if(index == TAGS_INDEX){
            mTagList.clear();
            if (charText.length() == 0) {
                mTagList.addAll(mTagResultList);
            } else {
                for (Tag item : mTagResultList) {
                    String title = item.tagName();
                    if (title.toLowerCase().contains(charText)) {
                        mTagList.add(item);
                    }
                }
            }
        } else if(index == SOUNDS_INDEX){
            mMusicList.clear();
            if (charText.length() == 0) {
                mMusicList.addAll(mMusicResultList);
            } else {
                for (Music item : mMusicResultList) {
                    String title = item.title();
                    if (title.toLowerCase().contains(charText)) {
                        mMusicList.add(item);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

}
