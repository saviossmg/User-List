package com.svmdev.userslist.users.viewmodel

import androidx.lifecycle.MutableLiveData
import com.svmdev.userslist.repository.data.DataCache
import com.svmdev.userslist.repository.data.UserListRepositoryPage
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.users.adapter.PageInfo

class UserReposViewModel : BaseViewModel() {

    val userRepositoryList = MutableLiveData<ArrayList<UserRepository>>()
    val pageInfoList = MutableLiveData<ArrayList<PageInfo>>()
    val repoUrl = MutableLiveData<String>()

    private val userRepositoryPageList = ArrayList<UserListRepositoryPage>()
    val pagesInfo = ArrayList<PageInfo>()
    lateinit var selectedPage: PageInfo

    fun performLoadInfoFirstTime(){
        onShowLoading(true)

        DataCache.repoListLoaded = true

        val repoInfo = DataCache.repoListUserListRepoPageList
        val listSize = repoInfo.size
        val pages = ArrayList<PageInfo>()

        for (index in 0 until listSize) {
            val currentPageInfo = PageInfo(
                index,
                repoInfo[index].page
            )
            pages.add(currentPageInfo)
        }

        DataCache.repoListPageSelected = pages[0]
        DataCache.repoListPageInfoList.addAll(pages)
        selectedPage = pages[0]
        pagesInfo.addAll(pages)
        userRepositoryPageList.addAll(repoInfo)
        pageInfoList.postValue(pages)
        val selectedUserRepoListPage = userRepositoryPageList[selectedPage.index].elements
        userRepositoryList.postValue(selectedUserRepoListPage)
    }

    fun loadInfoByCache() {
        onShowLoading(true)

        selectedPage = DataCache.repoListPageSelected
        pagesInfo.addAll(DataCache.repoListPageInfoList)
        userRepositoryPageList.addAll(DataCache.repoListUserListRepoPageList)
        pageInfoList.postValue(DataCache.repoListPageInfoList)

        val selectedUserRepoListPage = userRepositoryPageList[selectedPage.index].elements
        userRepositoryList.postValue(selectedUserRepoListPage)

    }

    fun performChangePage(selectedPosition: Int) {
        selectedPage = pagesInfo[selectedPosition]
        DataCache.repoListPageSelected = selectedPage
        onShowLoading(true)
        val selectedUserRepoListPage = userRepositoryPageList[selectedPage.index].elements
        userRepositoryList.postValue(selectedUserRepoListPage)
    }

    fun isLoadBefore(): Boolean {
        return DataCache.repoListLoaded
    }

    fun onShowLoading(show: Boolean) {
        loading.postValue(show)
    }

    fun resetPageInfo() {
        DataCache.repoListPageSelected = PageInfo(0,1)
        DataCache.repoListLoaded = false
    }

}