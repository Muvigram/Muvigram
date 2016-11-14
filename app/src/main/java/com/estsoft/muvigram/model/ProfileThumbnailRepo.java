package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.estsoft.muvigram.ui.search.SearchFragment;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jeongyi on 2016. 11. 9..
 */
@AutoValue
public abstract class ProfileThumbnailRepo implements Comparable<ProfileThumbnailRepo>, Parcelable {

    public abstract String id();
    public abstract String thumbnail();

    public static ProfileThumbnailRepo create(ProfileThumbnailRepo profileThumbnailRepo) { return  profileThumbnailRepo; };

    public static Builder builder() { return new AutoValue_ProfileThumbnailRepo.Builder();}

    public static TypeAdapter<ProfileThumbnailRepo> typeAdapter(Gson gson){
        return new AutoValue_ProfileThumbnailRepo.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull ProfileThumbnailRepo another) { return id().compareTo(another.id());}

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setId(String id);
        public abstract Builder setThumbnail(String thumbnail);
        public abstract ProfileThumbnailRepo build();
    }
}
