package com.svmdev.userslist.users.view

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    var mPrevConfig: Configuration? = null

    fun initPrevConfig() {
        mPrevConfig = Configuration(resources.configuration)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChanged(newConfig)
        mPrevConfig = Configuration(newConfig)
    }

    private fun configurationChanged(newConfig: Configuration) {
        if (isNightConfigChanged(newConfig)) { // night mode has changed
            recreate()
        }
    }

    private fun isNightConfigChanged(newConfig: Configuration): Boolean {
        return newConfig.diff(mPrevConfig) and ActivityInfo.CONFIG_UI_MODE != 0 && isOnDarkMode(
            newConfig
        ) != isOnDarkMode(
            mPrevConfig!!
        )
    }

    private fun isOnDarkMode(configuration: Configuration): Boolean {
        return configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}