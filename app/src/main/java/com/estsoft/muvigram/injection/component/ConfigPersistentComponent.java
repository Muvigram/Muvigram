package com.estsoft.muvigram.injection.component;

import com.estsoft.muvigram.injection.ConfigPersistent;
import com.estsoft.muvigram.injection.module.ActivityModule;

import dagger.Component;

/**
 * Created by gangGongUi on 2016. 10. 9..
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
