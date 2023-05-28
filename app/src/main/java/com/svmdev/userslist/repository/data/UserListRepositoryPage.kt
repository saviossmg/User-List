package com.svmdev.userslist.repository.data

import java.io.Serializable

data class UserListRepositoryPage(
    val page: Int,
    val elements: ArrayList<UserRepository>
) : Serializable