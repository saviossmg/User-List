package com.svmdev.userslist.users.viewmodel

import androidx.lifecycle.MutableLiveData
import com.svmdev.userslist.R
import com.svmdev.userslist.repository.data.DataCache
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.RetrofitClient
import com.svmdev.userslist.repository.service.common.CommonHelper.showMessageShort
import com.svmdev.userslist.repository.service.common.ServiceLinks

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListViewModel : BaseViewModel() {

    val usersList: MutableLiveData<ArrayList<User>> = MutableLiveData()
    val onSearchIsEmpty: MutableLiveData<Boolean> = MutableLiveData()
    val selectedUser: MutableLiveData<User> = MutableLiveData()
    val loadingProfile: MutableLiveData<Boolean> = MutableLiveData()
    private val repository = RetrofitClient().getRepository()

    fun loadUserList() {
        if (DataCache.cacheUsersList.isNotEmpty()) {
            usersList.postValue(DataCache.cacheUsersList)
        } else {
            performCallUserListApi()
        }
    }

    private fun performCallUserListApi() {
        loading.postValue(true)
        val apiCall = repository.getUserList()

        apiCall.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                DataCache.cacheUsersList.clear()
                if (response.code() == ServiceLinks.codeOk) {
                    usersList.postValue(response.body())
                    response.body()?.let { DataCache.cacheUsersList.addAll(it) }
                } else {
                    usersList.postValue(ArrayList())
                    DataCache.cacheUsersList.addAll(ArrayList())
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

    fun loadUserProfile(userLogin: String) {
        loadingProfile.postValue(true)
        val apiCall = repository.getUserInformation(userLogin)

        apiCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var userReturn: User? = null
                if (response.code() == ServiceLinks.codeOk) {
                    userReturn = response.body()
                }
                loadingProfile.postValue(false)
                selectedUser.postValue(userReturn)
                DataCache.cacheUserProfile = response.body()

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onError.postValue(t)
                showMessageShort(appContext, appContext.getString(R.string.error_api))
                loadingProfile.postValue(false)
            }
        })
    }

    fun getSelectUserLogin(): String {
        return selectedUser.value?.login ?: "-"
    }

}