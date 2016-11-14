package com.estsoft.muvigram.data.remote;

import com.estsoft.muvigram.model.Music;
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

public interface TrendingSoundsService {

    String ENDPOINT = "http://www.json-generator.com/api/json/get/";

    //데이터를 가져오는 인터페이스
    @GET("cvRDXVJazS")
    Observable<List<Music>> getMusics();

    //구현은 레트로핏이 해줌
    class Creator {
        public static TrendingSoundsService newTredingSoundsService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(FindFriendService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(TrendingSoundsService.class);
        }

    }

}
