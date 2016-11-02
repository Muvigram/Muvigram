package com.estsoft.muvigram.ui.feed.comment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.DynamicImageView;
import com.estsoft.muvigram.model.CommentRepo;
import com.estsoft.muvigram.util.transform.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import rx.Observable;

/**
 * Created by gangGongUi on 2016. 11. 2..
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private LinkedList<CommentRepo> mCommentRepos;
    private Context mContext;

    public CommentAdapter(LinkedList<CommentRepo> mCommentRepos, Context mContext) {
        this.mCommentRepos = mCommentRepos;
        this.mContext = mContext;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {

        Observable.just(mCommentRepos.get(position))
                .subscribe((item) -> {
                    Picasso.with(mContext)
                            .load(item.getMProfileImageUri())
                            .transform(new CircleTransform())
                            .into(holder.mProfileImageView);
                    holder.mUserNameView.setText(item.getMUsername());
                    holder.mCommentView.setText(item.getMText());
                });
    }

    @Override public int getItemCount() {
        return mCommentRepos.size();
    }

    public void addItem(@NonNull final CommentRepo mCommentRepo) {
        mCommentRepos.addFirst(mCommentRepo);
        notifyItemInserted(0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public DynamicImageView mProfileImageView;
        public TextView mCommentView;
        public TextView mUserNameView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCommentView = (TextView) itemView.findViewById(R.id.comment_text_view);
            mUserNameView = (TextView) itemView.findViewById(R.id.username_text_view);
            mProfileImageView = (DynamicImageView) itemView.findViewById(R.id.profile_image_view);
            mUserNameView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Black.ttf"));
        }
    }
}
