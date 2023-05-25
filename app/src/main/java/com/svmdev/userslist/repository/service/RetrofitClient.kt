package com.svmdev.userslist.repository.service

import com.svmdev.userslist.repository.service.common.ServiceLinks
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private var instance : RetrofitClient ?= null

    private val DEFAULT_TIMEOUT_SECONDS = 15L

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ServiceLinks.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${ServiceLinks.token}").build()
                chain.proceed(request)
            }.build())
            .build()
    }

    fun getRetrofitInstance(): Retrofit {
        return this.retrofit;
    }

    fun getRetrofitClient(): RetrofitClient {
        if(instance == null) {
            instance =  RetrofitClient()
        }
        return instance as RetrofitClient
    }

    fun getRepository(): UserListEndpoints = retrofit.create(UserListEndpoints::class.java)
}