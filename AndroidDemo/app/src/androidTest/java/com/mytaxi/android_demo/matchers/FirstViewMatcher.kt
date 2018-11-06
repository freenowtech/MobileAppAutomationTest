package com.mytaxi.android_demo.matchers

import android.view.View
import org.hamcrest.BaseMatcher
import org.hamcrest.Description


class FirstViewMatcher : BaseMatcher<View>() {

    private var firstViewMatched = false

    override fun matches(item: Any): Boolean {
        return if (!firstViewMatched) {
            firstViewMatched = true
            true
        } else {
            false
        }
    }

    override fun describeTo(description: Description) {
        description.appendText("is first view")
    }
}