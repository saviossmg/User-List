package com.svmdev.userslist.repository

import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.repository.service.RetrofitClient
import com.svmdev.userslist.repository.service.UserListEndpoints
import retrofit2.Call

class UserListRepository(private val retrofit: RetrofitClient) :
    UserListEndpoints {

    private val endpoints = retrofit.getRepository()

    override fun getUserList(): Call<ArrayList<User>> {
        return endpoints.getUserList()
    }

    override fun getUserInformation(userName: String): Call<User> {
        return endpoints.getUserInformation(userName)
    }

    override fun getUserRepositories(userName: String): Call<ArrayList<UserRepository>> {
        return endpoints.getUserRepositories(userName)
    }

    override fun getUserRepositoriesByPages(userName: String, page: Int): Call<ArrayList<UserRepository>> {
        return endpoints.getUserRepositoriesByPages(userName,page)
    }
}