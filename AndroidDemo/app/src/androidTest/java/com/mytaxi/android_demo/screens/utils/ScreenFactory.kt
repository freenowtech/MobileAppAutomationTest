package com.mytaxi.android_demo.screens.utils

import com.mytaxi.android_demo.screens.BaseScreen

/**
 * Helper class that makes sure a Screen is properly loaded before interacting with it
 */
class ScreenFactory {

    companion object {
        fun <T : BaseScreen> initScreen(screen: T) : T  {
            screen.checkIfLoaded()
            return screen
        }
    }
}