package com.estsoft.muvigram.data.remote;

import com.estsoft.muvigram.model.Category;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.util.MyGsonTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jaylim on 11/1/2016.
 */

public interface MusicSelectService {

    String ENDPOINT = "http://www.json-generator.com/api/";
    // String ENDOPINT = "http://address/media/music/";

    @GET("cezPVLVMHS")
    Observable<List<Category>> getCategries();
    // @GET("category")
    // Observable<List<Category>> getCategories();

    @GET("bVMwwPLLDm")
    Observable<List<Music>> getMusics();
    // @GET("music/{category_name}")
    // Observable<List<Music>> getMusics(@Path("category_no") Long categoryId);

    class Creator {
        public static MusicSelectService newMusicSelectService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MusicSelectService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(MusicSelectService.class);

        }
    }
}
