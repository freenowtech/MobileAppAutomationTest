package com.mytaxi.android_demo.tests

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import com.mytaxi.android_demo.activities.MainActivity
import com.mytaxi.android_demo.utils.TestData.VALID_PASSWORD
import com.mytaxi.android_demo.utils.TestData.VALID_USERNAME
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test

class LoginTest : BaseTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(ACCESS_FINE_LOCATION)

    @Test
    fun shouldLoginWithValidCredentials() {

        loginScreen().login(VALID_USERNAME, VALID_PASSWORD)

        assertThat("Should be logged in at 'Search Driver' screen",
                searchDriverScreen().isAt(), equalTo(true))
    }

    @Test
    fun shouldNotLoginWithWrongPassword() {

        loginScreen().login(VALID_USERNAME, "wrongPassword")

        assertThat("Should still be at the Login screen", loginScreen().isAt(), equalTo(true))
    }
}