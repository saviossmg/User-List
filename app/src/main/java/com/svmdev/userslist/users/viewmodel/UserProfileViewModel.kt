package com.svmdev.userslist.users.viewmodel

import androidx.lifecycle.MutableLiveData
import com.svmdev.userslist.R
import com.svmdev.userslist.repository.data.DataCache
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.data.UserListRepositoryPage
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.repository.service.RetrofitClient
import com.svmdev.userslist.repository.service.common.CommonHelper
import com.svmdev.userslist.repository.service.common.ServiceLinks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : BaseViewModel() {

    val selectedUser: MutableLiveData<User> = MutableLiveData()
    val userListRepositoryPagination: MutableLiveData<ArrayList<UserListRepositoryPage>> =
        MutableLiveData()
    val loadingMessage: MutableLiveData<String> = MutableLiveData()
    var userLogin: String = ""
    private val repository = RetrofitClient().getRepository()
    var executeProcess = true

    fun performSetSelectedUser(user: User) {
        val userData = if (user == null) DataCache.cacheUserProfile else user
        selectedUser.postValue(userData)
        userLogin = userData?.login ?: ""
    }

    fun performLoadUserRepositoryList() {
        loading.postValue(true)
        loadingMessage.postValue(
            appContext.getString(
                R.string.profile_repo_loading_info,
                userLogin
            )
        )

        val apiCall = repository.getUserRepositories(userLogin)
        apiCall.enqueue(object : Callback<ArrayList<UserRepository>> {
            override fun onResponse(
                call: Call<ArrayList<UserRepository>>,
                response: Response<ArrayList<UserRepository>>
            ) {
                val userRepoList = ArrayList<UserRepository>()
                val userRepoListPaginated = ArrayList<UserListRepositoryPage>()

                if (response.code() == ServiceLinks.codeOk) {
                    response.body()?.let { userRepoList.addAll(it) }
                    userRepoListPaginated.addAll(onExecutePagination())

                    DataCache.cacheUserRepositoryList.clear()
                    DataCache.cacheUserRepositoryPageList.clear()

                    DataCache.cacheUserRepositoryList.addAll(userRepoList)
                    DataCache.cacheUserRepositoryPageList.addAll(userRepoListPaginated)
                }
                userListRepositoryPagination.postValue(userRepoListPaginated)
                loading.postValue(false)
            }

            override fun onFailure(call: Call<ArrayList<UserRepository>>, t: Throwable) {
                loading.postValue(false)
                onError.postValue(t)
                CommonHelper.showMessageShort(
                    appContext,
                    appContext.getString(R.string.error_api)
                )
            }
        })
    }

    fun performDateFormat(dateStr: String): String? {
        return CommonHelper.formatDateToString(dateStr)
    }

    fun getUserProfileUrl(): String {
        return ServiceLinks.gitHubBaseUrl + selectedUser.value?.login
    }

    fun getUserBlogUrl(): String {
        return selectedUser.value?.blog ?: ServiceLinks.gitHubBaseUrl
    }

    private fun onExecutePagination(): ArrayList<UserListRepositoryPage> {
        val listUserRepoPages = ArrayList<UserListRepositoryPage>()
        var currentPage: Int
        var nextPage = 1
        executeProcess = true

        val thread = Thread {
            try {
                while (executeProcess) {
                    val response =
                        repository.getUserRepositoriesByPages(userLogin, nextPage).execute()
                    val responseInfo = response.body()
                    if (responseInfo.isNullOrEmpty()) {
                        executeProcess = false
                    } else {
                        currentPage = nextPage
                        val userRepoPage = UserListRepositoryPage(currentPage, responseInfo)
                        listUserRepoPages.add(userRepoPage)
                        nextPage++
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }
        thread.start()

        while (thread.isAlive) {
            loadingMessage.postValue(
                appContext.getString(
                    R.string.profile_repo_loading_page,
                    nextPage.toString()
                )
            )
        }

        return listUserRepoPages
    }

}