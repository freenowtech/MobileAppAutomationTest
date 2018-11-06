package com.mytaxi.android_demo.bot

import android.support.annotation.IdRes
import android.support.test.espresso.*
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.view.View
import android.widget.TextView
import com.mytaxi.android_demo.matchers.FirstViewMatcher
import com.mytaxi.android_demo.resources.WaitForViewToMatchCriteriaIdlingResource
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import org.hamcrest.Matchers

/**
 * Encapsulates all logic on interacting with the app with Espresso
 * Inspired from: https://github.com/SeleniumHQ/selenium/wiki/Bot-Style-Tests
 */
class Bot {

    companion object {

        private const val WAIT_TIMEOUT: Long = 5000

        private lateinit var idlingResource: IdlingResource

        private val idlingRegistry = IdlingRegistry.getInstance()

        fun findView(viewId: Int): ViewInteraction {
            return onView(withId(viewId))
        }

        fun type(view: ViewInteraction, text: String) {
            view.perform(typeText(text))
        }

        fun tap(view: ViewInteraction) {
            view.perform(click())
        }

        fun checkIsDisplayed(view: ViewInteraction) {
            view.check(matches(isDisplayed()))
        }

        fun isViewDisplayed(view: ViewInteraction): Boolean {
            return try {
                checkIsDisplayed(view)
                true
            } catch (e: NoMatchingViewException) {
                false
            } catch (e: AssertionFailedError) {
                false
            }
        }

        fun getText(view: ViewInteraction): String? {
            var stringHolder : String? = null
            view.perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isAssignableFrom(TextView::class.java)
                }

                override fun getDescription(): String {
                    return "getting text from a TextView"
                }

                override fun perform(uiController: UiController, view: View) {
                    val textView = view as TextView //Save, because of check in getConstraints()
                    stringHolder = textView.text.toString()
                }
            })
            return stringHolder
        }

        fun waitUntilViewIsDisplayed(@IdRes id: Int) {
            waitForViewToMatchCriteria(id, isDisplayed(), WAIT_TIMEOUT)
        }

        private fun waitForViewToMatchCriteria(@IdRes id: Int,
                                       criteriaMatcher: Matcher<View>,
                                       timeout: Long) {

            registerWaitForViewIdlingResource(id, criteriaMatcher, timeout)
            // Unregister will wait until is idle again
            unregisterWaitForViewIdlingResource()
        }

        private fun registerWaitForViewIdlingResource(@IdRes id: Int,
                                                      criteriaMatcher: Matcher<View>,
                                                      timeout: Long) {

            onView(FirstViewMatcher()).perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return Matchers.isA(View::class.java)
                }

                override fun getDescription(): String {
                    return "register IdleResource"
                }

                override fun perform(uiController: UiController, view: View) {
                    idlingResource = WaitForViewToMatchCriteriaIdlingResource(
                            view, id, criteriaMatcher, timeout)
                    idlingRegistry.register(idlingResource)
                }
            })
        }

        private fun unregisterWaitForViewIdlingResource() {
            onView(FirstViewMatcher()).perform(object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return Matchers.isA(View::class.java)
                }

                override fun getDescription(): String {
                    return "unregister IdleResource"
                }

                override fun perform(uiController: UiController, view: View) {
                    uiController.loopMainThreadUntilIdle()
                    idlingRegistry.unregister(idlingResource)
                }
            })
        }
    }
}