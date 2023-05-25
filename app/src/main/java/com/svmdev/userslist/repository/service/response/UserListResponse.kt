package com.svmdev.userslist.repository.service.response

import com.google.gson.annotations.SerializedName
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.common.BaseCallBack

class UserListResponse : BaseCallBack() {
    @SerializedName("data")
    lateinit var userList: List<User>
}