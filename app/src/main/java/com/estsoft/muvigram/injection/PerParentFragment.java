package com.estsoft.muvigram.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by jaylim on 11/2/2016.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerParentFragment {
}
