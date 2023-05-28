package com.svmdev.userslist.users.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var onError: MutableLiveData<Throwable> = MutableLiveData()
    lateinit var appContext: Context

    fun initContext(context: Context) {
        appContext = context
    }

}