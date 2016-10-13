package com.estsoft.muvigram.data;


import com.estsoft.muvigram.data.remote.LoginSignService;
import com.estsoft.muvigram.data.remote.NetWorkTestService;
import com.estsoft.muvigram.model.TestRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Singleton
public class DataManager {

    private final NetWorkTestService mNetWorkTestService;
    private final LoginSignService mLoginSignService;

    @Inject
    public DataManager(NetWorkTestService mNetWorkTestService, LoginSignService mLoginSignService) {
        this.mNetWorkTestService = mNetWorkTestService;
        this.mLoginSignService = mLoginSignService;

    }

    public Observable<TestRepo> getRibotTest() {
        return mLoginSignService.getRibotTest();
    }

    public Observable<String> getNetWorkTestService() {
        return mNetWorkTestService.getTestData();
    }


}
