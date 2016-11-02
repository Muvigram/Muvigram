package com.estsoft.muvigram.ui.musicselect.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Subcomponent;

/**
 * Created by jaylim on 11/2/2016.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerNestedFragment {
}
