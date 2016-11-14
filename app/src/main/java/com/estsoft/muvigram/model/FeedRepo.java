package com.estsoft.muvigram.model;

import android.os.Parcelable;

import com.estsoft.muvigram.ui.feed.FeedItem;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by gangGongUi on 2016. 11. 9..
 */

@AutoValue
public abstract class FeedRepo implements Comparable<FeedRepo>, Parcelable {

    public abstract String video_name();
    public abstract String video_uri();
    public abstract String name();
    public abstract String video_thumbnail();
    public abstract String profile_image();
    public abstract String specification();
    public abstract String album_cover();
    public abstract boolean featured();

    public static FeedRepo create(FeedRepo feedRepo) {
        return feedRepo;
    }

    public static Builder builder() {
        return new AutoValue_FeedRepo.Builder();
    }

    public static TypeAdapter<FeedRepo> typeAdapter(Gson gson) {
        return new AutoValue_FeedRepo.GsonTypeAdapter(gson);
    }

    @Override public int compareTo(FeedRepo o) {
        return video_uri().compareTo(o.video_uri());
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder video_name(String video_name);
        public abstract Builder video_uri(String video_uri);
        public abstract Builder name(String name);
        public abstract Builder video_thumbnail(String video_thumbnail);
        public abstract Builder profile_image(String profile_image);
        public abstract Builder specification(String specification);
        public abstract Builder album_cover(String album_cover);
        public abstract Builder featured(boolean featured);
        public abstract FeedRepo build();
    }

    public FeedItem toFeedItem() {
        return new FeedItem(video_name(), name(), video_thumbnail(), video_thumbnail(), profile_image(), specification(), album_cover() ,featured());
    }
}
