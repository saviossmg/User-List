package com.svmdev.userslist.repository.service.response

import com.svmdev.userslist.repository.service.common.BaseCallBack

class UserListResponse : BaseCallBack() {
    lateinit var userList: ArrayList<UserListResponse>
}