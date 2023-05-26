package com.svmdev.userslist.users.viewmodel

import androidx.lifecycle.MutableLiveData
import com.svmdev.userslist.R
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.RetrofitClient
import com.svmdev.userslist.repository.service.common.MessagesHelper.showMessageShort
import com.svmdev.userslist.repository.service.common.ServiceLinks

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListViewModel: BaseViewModel() {

    val usersList: MutableLiveData<ArrayList<User>> = MutableLiveData()
    val onSearchIsEmpty: MutableLiveData<Boolean> = MutableLiveData()
    val selectedUser: MutableLiveData<User> = MutableLiveData()
    private val repository = RetrofitClient().getRepository()

    fun loadUserList() {
        loading.postValue(true)
        val apiCall = repository.getUserList()

        apiCall.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if(response.code() == ServiceLinks.codeOk){
                    usersList.postValue(response.body())
                } else {
                    usersList.postValue(ArrayList())
                }
                loading.postValue(false)
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                onError.postValue(t)
                showMessageShort(appContext, appContext.getString(R.string.error_api))
                loading.postValue(false)
            }
        })
    }

}