package com.mytaxi.android_demo.resources

import android.os.SystemClock
import android.support.test.espresso.IdlingResource
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.core.IsNull
import android.support.annotation.IdRes

/**
 * Custom IdleResource that waits until a view matches a certain criteria (i.e isDisplayed())
 */
class WaitForViewToMatchCriteriaIdlingResource(var rootView: View,
                                               @IdRes val viewId: Int,
                                               private val criteriaMatcher: Matcher<View>,
                                               private val timeout: Long) : IdlingResource {

    private val startTime: Long = SystemClock.elapsedRealtime()

    private lateinit var callback: IdlingResource.ResourceCallback

    override fun isIdleNow(): Boolean {
        val view: View? = rootView.findViewById(viewId)
        val isTimeExpired = SystemClock.elapsedRealtime() - startTime >= timeout
        val isNotNull = view != null || criteriaMatcher is IsNull<*>
        return if (isTimeExpired || isNotNull && criteriaMatcher.matches(view)) {
            callback.onTransitionToIdle()
            true
        } else {
            false
        }
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

    override fun getName(): String {
        return this.javaClass.name
    }
}