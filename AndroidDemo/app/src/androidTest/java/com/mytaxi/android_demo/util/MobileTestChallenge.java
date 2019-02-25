package com.mytaxi.android_demo.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.DriverProfileActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mytaxi.android_demo.misc.Constants.LOG_TAG;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MobileTestChallenge {
    @Rule
    public final ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() throws Exception {

        Log.i(LOG_TAG, "------- START :: SETUP METHOD ------- ");
        Intents.init();
        rule.launchActivity(new Intent());

        intended(hasComponent(AuthenticationActivity.class.getName()));

        Map<String, String> user = UserCredentials.getUderCreds();

        onView(withId(R.id.edt_username)).perform(typeText(user.get("username")));
        onView(withId(R.id.edt_password)).perform(typeText(user.get("password")), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        Log.i(LOG_TAG, "User logged in successfully...!!!");

        Thread.sleep(10000);
        Intents.release();
        Log.i(LOG_TAG, "------- END :: BEFORE METHOD ------- ");
    }

    @Test
    public void searchDriver() throws Exception {

        Log.i(LOG_TAG, "------- START Testcase :: 'Search Driver' ------- ");
        Intents.init();

        String searchText = "sa";
        onView(withId(R.id.textSearch)).perform(typeText(searchText), closeSoftKeyboard());

        Matcher<Intent> matcher = hasComponent(DriverProfileActivity.class.getName());
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);

        // click on the 2nd result
        String driverName = "Sarah Scott";
        onView(withText(driverName))
                .inRoot(RootMatchers.withDecorView(not(is(rule.getActivity().getWindow().getDecorView()))))
                .perform(scrollTo())
                .perform(click());

        pressBack();

        intended(matcher);
        Intents.release();
        Log.i(LOG_TAG, "------- END Testcase :: 'Search Driver' ------- ");
    }

    @After
    public void tearDown() throws Exception {
        Log.i(LOG_TAG, "------- START :: TEARDOWN() METHOD ------- ");
        Intents.init();
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());

        onView(withText(R.string.text_item_title_logout)).perform(click());
        Log.i(LOG_TAG, "User is logged out");
        Intents.release();
        Log.i(LOG_TAG, "------- END :: END TEARDOWN() METHOD ------- ");
    }
}
