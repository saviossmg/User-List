package com.svmdev.userslist.users.viewmodel

import androidx.lifecycle.MutableLiveData
import com.svmdev.userslist.repository.data.DataCache
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.RetrofitClient
import java.text.SimpleDateFormat
import java.util.Date

class UserProfileViewModel: BaseViewModel() {

    val selectedUser: MutableLiveData<User> = MutableLiveData()
    private val repository = RetrofitClient().getRepository()

    fun performSetSelectedUser(user: User){
        if(user == null){
            selectedUser.postValue(DataCache.cacheUserProfile)
        } else {
            selectedUser.postValue(user)
        }

    }



    fun performDateFormat(dateStr: String) : String? {
        val dateSubStr = dateStr.split("T")
        val apiDateFormatPattern = "yyyy-MM-dd"
        val apiDate = SimpleDateFormat(apiDateFormatPattern).parse(dateSubStr[0]);

        val showDatePattern = SimpleDateFormat("dd/MM/yyyy")

        return apiDate?.let { showDatePattern.format(it) }
    }


}