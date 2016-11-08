package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by JEONGYI on 2016. 11. 7..
 */

@AutoValue
public abstract class Tag implements Comparable<Tag>, Parcelable{

    public abstract Long id();
    public abstract String tagName();
    public abstract Long numOfContents();

    public static Tag create(Tag tag) { return tag; }

    public static Builder builder() {
        return new AutoValue_Tag.Builder();
    }

    public static TypeAdapter<Tag> typeAdapter(Gson gson) {
        return new AutoValue_Tag.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Tag another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setId(Long id);
        public abstract Builder setTagName(String tagName);
        public abstract Builder setNumOfContents(Long numOfContents);
        public abstract Tag build();
    }

}
