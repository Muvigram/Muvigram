package com.estsoft.muvigram.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by gangGongUi on 2016. 10. 13..
 */
@AutoValue
public abstract class UserInfoRepo implements Comparable<UserInfoRepo>, Parcelable {
    public abstract String userid();
    public abstract String userName();
    public abstract String password();
    public abstract String email();
    public abstract String bio();
    public abstract String profileImage();

    public static UserInfoRepo create(UserInfoRepo userInfoRepo) { return  userInfoRepo; };

    public static Builder builder() { return new AutoValue_UserInfoRepo.Builder();}

    public static TypeAdapter<UserInfoRepo> typeAdapter(Gson gson){
        return new AutoValue_UserInfoRepo.GsonTypeAdapter(gson);
    }

    @Override
    public int compareTo(@NonNull UserInfoRepo another) { return userid().compareTo(another.userid());}

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setUserid(String userid);
        public abstract Builder setUserName(String userName);
        public abstract Builder setPassword(String password);
        public abstract Builder setEmail(String email);
        public abstract Builder setBio(String bio);
        public abstract Builder setProfileImage(String profileImage);
        public abstract UserInfoRepo build();
    }
}
