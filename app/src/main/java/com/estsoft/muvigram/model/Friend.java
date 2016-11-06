package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jaylim on 11/5/2016.
 */

@AutoValue
public abstract class Friend implements Comparable<Friend>, Parcelable {

    public abstract String id();
    public abstract String name();
    public abstract String profile();

    public static Friend create(Friend friend) {
        return friend;
    }

    public static Builder builder() {
        return new AutoValue_Friend.Builder();
    }

    public static TypeAdapter<Friend> typeAdapyer(Gson gson) {
        return new AutoValue_Friend.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Friend another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String id);
        public abstract Builder setName(String name);
        public abstract Builder setProfile(String profile);
        public abstract Friend build();
    }
}
