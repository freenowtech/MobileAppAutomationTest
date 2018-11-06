package com.mytaxi.android_demo.screens

import com.mytaxi.android_demo.R
import com.mytaxi.android_demo.bot.Bot
import java.time.LocalDate
import java.util.*

class DriverProfileScreen : BaseScreen() {

    private val nameLabel by lazy { Bot.findView(R.id.textViewDriverName) }
    private val locationLabel by lazy { Bot.findView(R.id.textViewDriverLocation) }
    private val registeredDateLabel by lazy { Bot.findView(R.id.textViewDriverDate) }
    private val callButton by lazy { Bot.findView(R.id.fab) }

    override fun checkIfLoaded() = Bot.waitUntilViewIsDisplayed(R.id.imageViewDriverAvatar)

    fun isAt() : Boolean = Bot.isViewDisplayed(callButton)

    fun phoneCall() = Bot.tap(callButton)

    fun getName() : String? = Bot.getText(nameLabel)
    fun getLocation() : String? = Bot.getText(locationLabel)
    fun getRegisteredDate() : LocalDate = LocalDate.parse(Bot.getText(registeredDateLabel))
}