package com.estsoft.muvigram.ui.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.estsoft.muvigram.ui.search.SearchListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 28..
 */

public class MayKnowItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String PROFILE_IMAGE = "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg";
    boolean isClicked = false;

    private Context context;
    private List<MayKnowListItem> mMayKnowListItemList;

    public MayKnowItemAdapter(List<MayKnowListItem> mMayKnowListItemList, Context mContext) {
        this.context = mContext;
        this.mMayKnowListItemList = mMayKnowListItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int position){

        View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_follow, parent, false);
        return new MayKnowItemAdapter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {

        ViewHolder mHolder = (ViewHolder)holder;
        mHolder.setIsRecyclable(false);

        Picasso.with(context)
                .load(PROFILE_IMAGE)
                .transform(new CircleTransform()).into(mHolder.profile);

        mHolder.name.setText (mMayKnowListItemList.get(position).getName());
        mHolder.id.setText(mMayKnowListItemList.get(position).getId());

        mHolder.followButton.setOnClickListener(v -> {

            isClicked = !isClicked;
            if(isClicked){
                v.setBackgroundResource(R.drawable.notify_follow_button_done);
            }else if(!isClicked){
                v.setBackgroundResource(R.drawable.notify_follow_button_event);
            }

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
        return mMayKnowListItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_image)
        ImageView profile;
        @BindView(R.id.id)
        TextView name;
        @BindView(R.id.subtitle) TextView id;
        @BindView(R.id.follow_button)
        ImageButton followButton;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
