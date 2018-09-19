package com.mytaxi.android_demo;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.utils.idle_resources.LoginIdleManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MyTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(LoginIdleManager.getIdlingResource());
    }

    @Test
    public void login_success(){
        Log.println(Log.INFO, "Test", "Performing login success test");

        onView(withId(R.id.edt_username)).perform(ViewActions.typeText("crazydog335"));
        onView(withId(R.id.edt_password)).perform(ViewActions.typeText("venture"));
        onView(withId(R.id.btn_login)).perform(click());
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_username)).check(matches(withText("crazydog335")));
        onView(withText("Logout")).perform(click());
    }

    @Test
    public void selectSecondResult() {

        Log.println(Log.INFO,"@Test","Performing login success test");

        onView(withId(R.id.edt_username)).perform(ViewActions.typeText("crazydog335"));
        onView(withId(R.id.edt_password)).perform(ViewActions.typeText("venture"));
        onView(withId(R.id.btn_login)).perform(click());

        Log.println(Log.INFO,"@Test","Search driver sa");
        onView(withId(R.id.textSearch)).perform(ViewActions.typeText("sa"));

        Log.println(Log.INFO,"@Test","Select second driver from the list by name");

        onView(withText("Sarah Scott"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(click());

        onView(withId(R.id.fab)).perform(ViewActions.click());
    }

    @After
    public void finishExecution() {
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withText("Logout")).perform(click());
    }
}
