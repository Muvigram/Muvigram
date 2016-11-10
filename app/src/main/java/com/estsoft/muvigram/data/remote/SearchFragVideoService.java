package com.estsoft.muvigram.data.remote;

import android.provider.MediaStore;

import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.ui.search.SearchHeaderVideoItem;
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
 * Created by JEONGYI on 2016. 11. 8..
 */

public interface SearchFragVideoService {

    String ENDPOINT = "http://www.json-generator.com/api/json/get/";

    @GET("cjZIRSBthK")
    Observable<SearchHeaderVideoItem> getVideo();

    class Creator {
        public static SearchFragVideoService newSearchFragVideoService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TrendingTagsService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(SearchFragVideoService.class);
        }
    }

}
