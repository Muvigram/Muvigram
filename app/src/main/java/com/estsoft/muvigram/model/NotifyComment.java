package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jeongyi on 11/1/2016.
 */

@AutoValue
public abstract class NotifyComment implements Comparable<NotifyComment>, Parcelable {

    public abstract Long id();
    public abstract Long date();
    public abstract String user();
    public abstract String content();
    public abstract String profile();
    public abstract String thumbnail();


    public static NotifyComment create(NotifyComment notifyComment) {
        return notifyComment;
    }

    public static Builder builder() {
        return new AutoValue_NotifyComment.Builder();
    }

    public static TypeAdapter<NotifyComment> typeAdapter(Gson gson) {
        return new AutoValue_NotifyComment.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull NotifyComment another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Long id);
        public abstract Builder setDate(Long date);
        public abstract Builder setUser(String user);
        public abstract Builder setContent(String content);
        public abstract Builder setProfile(String profile);
        public abstract Builder setThumbnail(String thumbnail);
        public abstract NotifyComment build();
    }

}
