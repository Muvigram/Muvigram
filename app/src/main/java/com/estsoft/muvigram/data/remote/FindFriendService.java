package com.estsoft.muvigram.data.remote;

import com.estsoft.muvigram.model.Friend;
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
 * Created by jaylim on 11/5/2016.
 */

public interface FindFriendService {

    String ENDPOINT = "http://www.json-generator.com/api/json/get/";

    @GET("bTUAfhuQbS")
    Observable<List<Friend>> getFriends();

    class Creator {
        public static FindFriendService newFindFriendService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(FindFriendService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(FindFriendService.class);
        }

    }
}
