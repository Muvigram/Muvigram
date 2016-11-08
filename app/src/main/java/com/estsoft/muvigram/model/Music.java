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
public abstract class Music implements Comparable<Music>, Parcelable {

    public abstract Long id();
    public abstract String title();
    @Nullable public abstract String artist();
    public abstract String albumCover();



    public static Music create(Music music) {
        return music;
    }

    public static Builder builder() {
        return new AutoValue_Music.Builder();
    }

    public static TypeAdapter<Music> typeAdapter(Gson gson) {
        return new AutoValue_Music.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull Music another) {
        return id().compareTo(another.id());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Long id);
        public abstract Builder setTitle(String title);
        public abstract Builder setArtist(String artist);
        public abstract Builder setAlbumCover(String albumCover);
        public abstract Music build();
    }

}
