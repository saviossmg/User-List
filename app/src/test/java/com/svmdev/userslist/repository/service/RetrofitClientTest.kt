package com.svmdev.userslist.repository.service

import com.svmdev.userslist.repository.UserListRepository
import com.svmdev.userslist.repository.service.common.ServiceLinks
import org.junit.Assert.assertEquals
import org.junit.Test


private const val URL_MESSAGE = "URL base está diferente"
private const val GET_USER_MESSAGE = "GetAllUsers está vazio"
private const val GET_USER_MESSAGE_200 = "GetAllUsers esperava codigo 200 mas o codigo veio: "
private const val GET_USER_MESSAGE_OFF = "GetAllUsers está indisponivel"
private const val GET_USER_INFO_MESSAGE= "GetUserInformation está vazio"
private const val GET_USER_INFO_MESSAGE_200= "GetUserInformation esperava codigo 200 mas o codigo veio: "
private const val GET_USER_INFO_MESSAGE_OFF = "GetUserInformation está indisponivel"

private const val GET_USER_REPO_MESSAGE= "GetUserRepositories está vazio"
private const val GET_USER_REPO_MESSAGE_200= "GetUserRepositories esperava codigo 200 mas o codigo veio: "
private const val GET_USER_REPO_MESSAGE_OFF = "GGetUserRepositories está indisponivel"


class RetrofitClientTest {
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
        val response = service.getUserList().execute()
        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        assert(errorBody == null){GET_USER_MESSAGE_OFF}
        assert(responseWrapper != null){GET_USER_MESSAGE}
        assert(response.code() == 200) {GET_USER_MESSAGE_200+response.code()}
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

    @Test
    fun testGetUserRepositories(){
        val response = service.getUserRepositories("jnewland").execute()
        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        println("Body :"+response.body().toString())

        assert(errorBody == null){GET_USER_REPO_MESSAGE_OFF}
        assert(responseWrapper != null){GET_USER_REPO_MESSAGE}
        assert(response.code() == 200) {GET_USER_REPO_MESSAGE_200+response.code()}
    }

    @Test
    fun testGetUserRepositoriesWithPages() {
        var page = 1
        var paginator = service.getUserRepositories("jnewland").execute()
        var response = paginator
        var stop = false
        while (!stop) {

            paginator = service.getUserRepositoriesByPages("jnewland", page).execute()
            val responseInfo = paginator.body()
            val responseCode = paginator.code()

            if (responseInfo != null && (responseCode == 200 && responseInfo.isNotEmpty())) {
                response = paginator;
                page++
            } else {
                stop = true
                page--
            }
        }

        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        println("testGetUserRepositoriesWithPages TOTAL PAGINAS: "+page)

        assert(errorBody == null) { GET_USER_REPO_MESSAGE_OFF }
        assert(responseWrapper != null) { GET_USER_REPO_MESSAGE }
        assert(response.code() == 200) { GET_USER_REPO_MESSAGE_200 + response.code() }
        assert(page > 0) { "Paginação está nula" }
    }

}