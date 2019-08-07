package com.freenow.android_demo.FreeNowChallenge.robots;

import com.freenow.android_demo.FreeNowChallenge.common.ScreenRobot;
import com.freenow.android_demo.R;

import static com.freenow.android_demo.FreeNowChallenge.constants.constants.DRIVER_SEARCH;
import static com.freenow.android_demo.FreeNowChallenge.constants.constants.PASSWORD;
import static com.freenow.android_demo.FreeNowChallenge.constants.constants.USER_NAME;
import static com.freenow.android_demo.FreeNowChallenge.constants.constants.WRONG_PASSWORD;
import static com.freenow.android_demo.FreeNowChallenge.constants.constants.WRONG_USER_NAME;

public class robots extends ScreenRobot <robots> {

    private static final int USERNAME_FIELD = R.id.edt_username;
    private static final int PASSWORD_FIELD = R.id.edt_password;
    private static final int LOGIN_BUTTON = R.id.btn_login;
    private static final int LOGOUT_BUTTON = R.id.design_menu_item_text;
    private static final int SEARCH_DRIVER_FIELD = R.id.textSearch;

    public robots checkUsernameField(){
        checkIsDisplayed(USERNAME_FIELD);
        return this;
    }

    public robots checkPasswordField(){
        checkIsDisplayed(PASSWORD_FIELD);
        return this;
    }

    public robots checkLoginButton(){
        checkIsDisplayed(LOGIN_BUTTON);
        return this;
    }

    public robots clickLoginButton() throws InterruptedException {
        clickOnView(LOGIN_BUTTON);
        sleep(5);
        return this;
    }

    public robots clickLogoutButton(){
        clickOnView(LOGOUT_BUTTON);
        return this;
    }

    public robots typeUsername(){
        enterTextIntoView(USERNAME_FIELD, USER_NAME);
        return this;
    }

    public robots typeDriverSearch(){
        enterTextIntoView(SEARCH_DRIVER_FIELD, DRIVER_SEARCH);
        return this;
    }

    public robots typePassword(){
        enterTextIntoView(PASSWORD_FIELD, PASSWORD);
        return this;
    }

    public robots typeWrongUsername(){
        enterTextIntoView(USERNAME_FIELD, WRONG_USER_NAME);
        return this;
    }

    public robots typeWrongPassword(){
        enterTextIntoView(PASSWORD_FIELD, WRONG_PASSWORD);
        return this;
    }
}
