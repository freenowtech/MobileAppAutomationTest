package com.mytaxi.android_demo.screens

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.RootMatchers.isPlatformPopup
import com.mytaxi.android_demo.R
import com.mytaxi.android_demo.bot.Bot
import com.mytaxi.android_demo.matchers.DriverMatchers
import com.mytaxi.android_demo.models.Driver
import com.mytaxi.android_demo.screens.utils.ScreenFactory.Companion.initScreen
import org.hamcrest.CoreMatchers.*

class SearchDriverScreen : BaseScreen() {

    private val textSearchInput by lazy { Bot.findView(R.id.textSearch) }

    override fun checkIfLoaded() = Bot.waitUntilViewIsDisplayed(R.id.searchContainer)

    fun isAt() : Boolean = Bot.isViewDisplayed(textSearchInput)

    fun search(text: String) : SearchDriverScreen {
        Bot.type(textSearchInput, text)
        return this
    }

    fun select(driverName: String) : DriverProfileScreen {
        onData(
                allOf(
                        `is`(instanceOf(Driver::class.java)),
                        DriverMatchers.withName(equalTo(driverName))
                )
        )
                .inRoot(isPlatformPopup())
                .perform(click())

        return initScreen(DriverProfileScreen())
    }
}