package com.mytaxi.android_demo.screens

/**
 * Base class for implementing Screens as pageObjects
 * Inspired from: https://github.com/SeleniumHQ/selenium/wiki/PageObjects
 */
abstract class BaseScreen {

    abstract fun checkIfLoaded()
}