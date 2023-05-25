package com.svmdev.userslist.repository

import com.google.gson.GsonBuilder
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.UserListEndpoints
import com.svmdev.userslist.repository.service.response.UserListResponse
import retrofit2.Call
import retrofit2.Retrofit

class UserListRepository(private val retrofit: Retrofit) :
    UserListEndpoints {

    private val endpoints by lazy { retrofit.create(UserListEndpoints::class.java) }
    private val  gsonBuilder = GsonBuilder()
    private val gson = gsonBuilder.create()

    override fun getUserList(): Call<UserListResponse> {
        return endpoints.getUserList()
    }

    override fun getUserInformation(userName: String): Call<User> {
        return endpoints.getUserInformation(userName)
    }

    override fun getUserRepositories(userName: String): Call<User> {
        return endpoints.getUserRepositories(userName)
    }
}