package com.mytaxi.android_demo.tests

import android.support.test.runner.AndroidJUnit4
import com.mytaxi.android_demo.rules.ClearSharedPreferencesRule
import com.mytaxi.android_demo.screens.*
import com.mytaxi.android_demo.screens.utils.ScreenFactory.Companion.initScreen
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    @get:Rule
    val clearSharedPreferencesRule = ClearSharedPreferencesRule()

    // Screen getters
    protected fun loginScreen() = initScreen(LoginScreen())
    protected fun searchDriverScreen() = initScreen(SearchDriverScreen())
    protected fun driverProfileScreen() = initScreen(DriverProfileScreen())
}