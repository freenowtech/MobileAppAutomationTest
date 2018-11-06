package com.mytaxi.android_demo.rules

import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.File

/**
 * This rule clears all app's SharedPreferences before running each test
 * Inspiration from Barista library: https://bit.ly/2D9D2l4
 */
class ClearSharedPreferencesRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object:Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                clearData()
                base.evaluate()
                clearData()
            }
        }
    }

    private fun clearData() {
        val allPrefs: List<SharedPreferences> = getAllPreferencesFiles()
        allPrefs.forEach { prefs -> prefs.edit().clear().apply() }
    }

    private fun getAllPreferencesFiles(): List<SharedPreferences> {
        val context = InstrumentationRegistry.getTargetContext().applicationContext
        val rootPath = context.applicationInfo.dataDir + "/shared_prefs"
        val prefsFolder = File(rootPath)

        val children = prefsFolder.list() ?: return emptyList()

        val allPrefs = ArrayList<SharedPreferences>()

        children.map {
            if (it.endsWith(".xml")) it.substring(0, it.indexOf(".xml")) else it
        }.mapTo(allPrefs) { context.getSharedPreferences(it, 0) }

        return allPrefs
    }
}