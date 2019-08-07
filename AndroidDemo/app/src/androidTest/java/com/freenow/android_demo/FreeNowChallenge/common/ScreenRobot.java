package com.freenow.android_demo.FreeNowChallenge.common;

import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public abstract class ScreenRobot <T extends ScreenRobot> {

    public T sleep(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000L);
        return (T) this;
    }

    public T checkIsDisplayed(@IdRes int... viewIds) {
        for (int viewId : viewIds) {
            onView(withId(viewId)).check(matches(isDisplayed()));
        }
        return (T) this;
    }

    public T clickOnView(@IdRes int viewId) {
        onView(withId(viewId)).perform(click());
        return (T) this;
    }

    public T clickOnMenu() {
        onView(withContentDescription("Open navigation drawer")).perform(click());
        return (T) this;
    }

    public T pressBack() {
        Espresso.pressBack();
        return (T) this;
    }

    public T enterTextIntoView(@IdRes int viewId, String text) {
        onView(withId(viewId)).perform(typeText(text));
        Espresso.closeSoftKeyboard();
        return (T) this;
    }
}