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
    val userName = "defunkt"
    val service = UserListRepository(RetrofitClient())

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
        val response = service.getUserInformation(userName).execute()
        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        assert(errorBody == null){GET_USER_INFO_MESSAGE_OFF}
        assert(responseWrapper != null){GET_USER_INFO_MESSAGE}
        assert(response.code() == 200) {GET_USER_INFO_MESSAGE_200+response.code()}
    }

    @Test
    fun testGetUserRepositories(){
        val response = service.getUserRepositories(userName).execute()
        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        assert(errorBody == null){GET_USER_REPO_MESSAGE_OFF}
        assert(responseWrapper != null){GET_USER_REPO_MESSAGE}
        assert(response.code() == 200) {GET_USER_REPO_MESSAGE_200+response.code()}
    }

    @Test
    fun testGetUserRepositoriesWithPages() {
        var currentPage = 0
        var nextPage = 1
        var paginator = service.getUserRepositories(userName).execute()
        var response = paginator
        var stop = false
        while (!stop) {

            paginator = service.getUserRepositoriesByPages(userName, nextPage).execute()
            val responseInfo = paginator.body()
            val responseCode = paginator.code()

            if (responseInfo != null && (responseCode == 200 && responseInfo.isNotEmpty())) {
                response = paginator;
                currentPage = nextPage
                nextPage++
            } else {
                stop = true
            }
        }

        val errorBody = response.errorBody()
        val responseWrapper = response.body()

        println("testGetUserRepositoriesWithPages TOTAL PAGINAS: "+currentPage)

        assert(errorBody == null) { GET_USER_REPO_MESSAGE_OFF }
        assert(responseWrapper != null) { GET_USER_REPO_MESSAGE }
        assert(response.code() == 200) { GET_USER_REPO_MESSAGE_200 + response.code() }
        assert(currentPage > 0) { "Paginação está nula" }
    }

}