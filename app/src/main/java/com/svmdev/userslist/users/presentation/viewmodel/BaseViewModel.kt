package com.svmdev.userslist.users.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var context: Context

    fun initContext(context: Context){
        this.context = context
    }

}