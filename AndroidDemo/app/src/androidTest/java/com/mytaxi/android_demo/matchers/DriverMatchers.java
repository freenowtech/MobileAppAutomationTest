package com.mytaxi.android_demo.matchers;

import com.mytaxi.android_demo.models.Driver;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class DriverMatchers {

    public static Matcher withName(final Matcher nameMatcher){
        return new TypeSafeMatcher<Driver>(){
            @Override
            public void describeTo(Description description) {
                description.appendText("with name '" + nameMatcher + "'");
            }

            @Override
            public boolean matchesSafely(Driver driver) {
                return nameMatcher.matches(driver.getName());
            }
        };
    }
}
