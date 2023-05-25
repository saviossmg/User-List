package com.svmdev.userslist.repository.service

import com.svmdev.userslist.repository.UserListRepository
import com.svmdev.userslist.repository.service.common.ServiceLinks
import org.junit.Assert.assertEquals
import org.junit.Test


private const val URL_MESSAGE = "URL base está diferente"
private const val GET_USER_MESSAGE_ = "GetAllUsers está vazio"
private const val GET_USER_MESSAGE_OFF = "GetAllUsers está indisponivel"
private const val GET_USER_INFO_MESSAGE= "GetUserInformation está vazio"
private const val GET_USER_INFO_MESSAGE_200= "GetUserInformation esperava codigo 200 mas o codigo veio: "
private const val GET_USER_INFO_MESSAGE_OFF = "GetUserInformation está indisponivel"


class RetrofitInitializerTest {
    val service = UserListRepository(RetrofitClient().getRetrofitInstance())

    @Test
    fun testGetRetrofitInstanceUrl() {
        val riBaseUrl = RetrofitClient().getRetrofitInstance().baseUrl().toString()
        assertEquals(
            URL_MESSAGE,
            ServiceLinks.baseUrl,
            riBaseUrl
        )
    }

    @Test
    fun testGetAllUsers() {
        //Get an instance of PlacesService by proiving the Retrofit instance
        //service = UserListRepository(RetrofitClient().getRetrofitInstance())

    }


    @Test
    fun testGetUserProfile() {
        val response = service.getUserInformation("jnewland").execute()
        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        assert(errorBody == null){GET_USER_INFO_MESSAGE_OFF}
        assert(responseWrapper != null){GET_USER_INFO_MESSAGE}
        assert(response.code() == 200) {GET_USER_INFO_MESSAGE_200+response.code()}
    }
}