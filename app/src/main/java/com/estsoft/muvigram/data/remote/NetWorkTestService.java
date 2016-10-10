package com.estsoft.muvigram.data.remote;


import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import rx.Observable;


/**
 * Created by gangGongUi on 2016. 10. 10..
 */
@Singleton
public class NetWorkTestService {



    public rx.Observable getTestData() {
        List<String> list = Arrays.asList("1", "2", "3"," 4", "5", "6");
        return Observable.from(list);
    }

}
