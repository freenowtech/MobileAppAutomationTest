package com.mytaxi.android_demo.tests

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import com.mytaxi.android_demo.activities.MainActivity
import com.mytaxi.android_demo.utils.TestData.driverCamila
import com.mytaxi.android_demo.utils.TestData.validPassword
import com.mytaxi.android_demo.utils.TestData.validUsername
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZoneId

class DriverProfileTest : BaseTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(ACCESS_FINE_LOCATION)

    @Before
    fun login() = loginScreen().login(validUsername, validPassword)

    private val testDriver = driverCamila

    @Test
    fun shouldNavigateToDriverProfile() {

        searchDriverScreen().run {
            search("ca")
            select(testDriver.name)
        }

        assertThat("Should be at 'Driver Profile' screen",
                driverProfileScreen().isAt(), equalTo(true))
        assertThat("Driver 'name' is not the expected one",
                driverProfileScreen().getName(), equalTo(testDriver.name))
        assertThat("Driver 'location' is not the expected one",
                driverProfileScreen().getLocation(), equalTo(testDriver.location))
        assertThat("Driver 'registered date' is not the expected one",
                driverProfileScreen().getRegisteredDate(),
                equalTo(testDriver.registeredDate
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))
    }

    @Test
    fun shouldCallDriver() {

        searchDriverScreen().run {
            search("ca")
            select(testDriver.name)
        }
        driverProfileScreen().phoneCall()

        // No Asserts here so not a very useful test, asserting that functionality is working could
        // be achieved with this approach: https://bit.ly/2D4ff66
    }
}