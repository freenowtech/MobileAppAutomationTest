package com.mytaxi.android_demo.screens

import com.mytaxi.android_demo.R
import com.mytaxi.android_demo.bot.Bot

class LoginScreen : BaseScreen() {

    private val usernameInput by lazy { Bot.findView(R.id.edt_username) }
    private val passwordInput by lazy { Bot.findView(R.id.edt_password) }
    private val loginButton by lazy { Bot.findView(R.id.btn_login) }

    override fun checkIfLoaded() = Bot.waitUntilViewIsDisplayed(R.id.btn_login)

    fun isAt() : Boolean = Bot.isViewDisplayed(loginButton)

    fun login(username: String, password: String) {
        Bot.type(usernameInput, username)
        Bot.type(passwordInput, password)
        Bot.tap(loginButton)
    }
}