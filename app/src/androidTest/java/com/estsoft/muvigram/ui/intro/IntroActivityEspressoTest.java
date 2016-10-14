package com.estsoft.muvigram.ui.intro;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by gangGongUi on 2016. 10. 11..
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class IntroActivityEspressoTest {


    @Rule public ActivityTestRule<IntroActivity> testRule = new ActivityTestRule<IntroActivity>(IntroActivity.class);

    @Test public void checkButtonText() {
        onView(withText("회원가입")).check(matches(isDisplayed()));
        onView(withText("로그인")).check(matches(isDisplayed()));
    }


}