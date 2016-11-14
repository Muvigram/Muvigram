package com.estsoft.muvigram.data.remote;

import com.estsoft.muvigram.model.FeedRepo;
import com.estsoft.muvigram.util.MyGsonTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by gangGongUi on 2016. 11. 9..
 */
public interface FeedService {

    String ENDPOINT = "http://www.json-generator.com/api/json/get/";

    @GET("bTwJRkJMeq")
    Observable<List<FeedRepo>> getFeedRepos();

    class Creator {
        public static FeedService newFeedService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(FeedService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(FeedService.class);

        }
    }
}
