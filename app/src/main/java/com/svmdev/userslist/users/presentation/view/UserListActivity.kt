package com.svmdev.userslist.users.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.svmdev.userslist.R

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSplashAnimation()
        setContentView(R.layout.activity_user_list)
    }

    private fun onSplashAnimation() {
        val startAnimation = installSplashScreen()
    }

}