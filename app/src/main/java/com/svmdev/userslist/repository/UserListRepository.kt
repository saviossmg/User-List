package com.svmdev.userslist.repository

import com.google.gson.GsonBuilder
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.repository.service.UserListEndpoints
import retrofit2.Call
import retrofit2.Retrofit

class UserListRepository(private val retrofit: Retrofit) :
    UserListEndpoints {

    private val endpoints by lazy { retrofit.create(UserListEndpoints::class.java) }
    private val  gsonBuilder = GsonBuilder()
    private val gson = gsonBuilder.create()

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