package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jaylim on 11/1/2016.
 */

@AutoValue
public abstract class Category implements Comparable<Category>, Parcelable {

    public abstract Long id();
    public abstract String name();

    public static Category create(Category category) {
        return category;
    }

    public static Builder builder() {
        return new AutoValue_Category.Builder();
    }

    public static TypeAdapter<Category> typeAdapter(Gson gson) {
        return new AutoValue_Category.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Category another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Long id);
        public abstract Builder setName(String name);
        public abstract Category build();
    }
}
