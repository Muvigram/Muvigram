package com.estsoft.muvigram.data.remote;

import com.estsoft.muvigram.model.TestRepo;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by gangGongUi on 2016. 10. 12..
 */
public interface LoginTestService {

    String ENDPOINT = "http://echo.jsontest.com/key/value/one/";

    @GET("two")
    Observable<TestRepo> getLoginTest();

    class Creator {
        public static LoginTestService newLoginService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(LoginTestService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(LoginTestService.class);
        }

    }

}
