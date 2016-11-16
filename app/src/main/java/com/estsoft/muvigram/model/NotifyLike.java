package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jeongyi on 11/1/2016.
 */

@AutoValue
public abstract class NotifyLike implements Comparable<NotifyLike>, Parcelable {

    public abstract Long id();
    public abstract Long date();
    public abstract String user();
    public abstract String profile();
    public abstract String thumbnail();


    public static NotifyLike create(NotifyLike notifyLike) {
        return notifyLike;
    }

    public static Builder builder() {
        return new AutoValue_NotifyLike.Builder();
    }

    public static TypeAdapter<NotifyLike> typeAdapter(Gson gson) {
        return new AutoValue_NotifyLike.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull NotifyLike another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Long id);
        public abstract Builder setDate(Long date);
        public abstract Builder setUser(String user);
        public abstract Builder setProfile(String profile);
        public abstract Builder setThumbnail(String thumbnail);
        public abstract NotifyLike build();
    }

}
