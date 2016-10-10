package com.estsoft.muvigram.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {

}
