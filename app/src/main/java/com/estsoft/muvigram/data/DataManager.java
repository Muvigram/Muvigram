package com.estsoft.muvigram.data;


import com.estsoft.muvigram.data.remote.NetWorkTestService;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Singleton
public class DataManager {

    private final NetWorkTestService mNetWorkTestService;

    @Inject
    public DataManager(NetWorkTestService mNetWorkTestService) {
        this.mNetWorkTestService = mNetWorkTestService;
    }

    public rx.Observable<String> getNetWorkTestService() {
        return mNetWorkTestService.getTestData();
    }
}
