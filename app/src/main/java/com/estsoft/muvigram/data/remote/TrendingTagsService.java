package com.estsoft.muvigram.data.remote;

import com.estsoft.muvigram.model.Tag;
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
 * Created by JEONGYI on 2016. 11. 7..
 */

public interface TrendingTagsService {

    String ENDPOINT = "http://www.json-generator.com/api/json/get/";

    @GET("bQfQPVSmJK")
    Observable<List<Tag>> getTags();

    class Creator {
        public static TrendingTagsService newTrendingTagsService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TrendingTagsService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(TrendingTagsService.class);
        }
    }

}
