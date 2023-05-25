package com.svmdev.userslist.repository.service

import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.repository.service.common.ServiceLinks
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserListEndpoints {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET(ServiceLinks.usersUrl)
    fun getUserList(): Call<ArrayList<User>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET(ServiceLinks.usersUrl+"/{userName}")
    fun getUserInformation(@Path("userName") userName: String ): Call<User>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET(ServiceLinks.usersUrl+"/{userName}/repos")
    fun getUserRepositories(@Path("userName") userName: String ): Call<ArrayList<UserRepository>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET(ServiceLinks.usersUrl+"/{userName}/repos")
    fun getUserRepositoriesByPages(@Path("userName") userName: String, @Query("page") page: Int ): Call<ArrayList<UserRepository>>

}