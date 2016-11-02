package com.estsoft.muvigram.ui.musicselect.online;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerNestedFragment;
import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.ui.musicselectonline.MusicSelectOnlineListActivitiy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jaylim on 11/2/2016.
 */

@PerNestedFragment
public class MusicSelectOnlineCategoryAdapter extends RecyclerView.Adapter<MusicSelectOnlineCategoryAdapter.CategoryViewHolder> {

    private List<Category> mCategories;

    @Inject
    public MusicSelectOnlineCategoryAdapter() {
        mCategories = new ArrayList<>();
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_musicselect_online_category_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.bindCategory(category, position);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private Category mCategory;

        @BindView(R.id.musicselect_online_category_item) LinearLayout mCategoryButton;
        @BindView(R.id.musicselect_online_category_item_image) ImageView mCategoryImage;
        @BindView(R.id.musicselect_online_category_item_name) TextView mCategoryName;


        public void bindCategory(Category category, int position) {
            mCategory = category;
            mCategoryName.setText(category.name());
            mCategoryButton.setOnClickListener(v -> {
                Context context = mCategoryButton.getContext();
                Intent intent = MusicSelectOnlineListActivitiy.newIntent(context, mCategory.id());
                context.startActivity(intent);
            });
        }

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
