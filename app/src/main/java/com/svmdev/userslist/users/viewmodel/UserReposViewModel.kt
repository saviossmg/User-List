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
    val selectSpinnerIndex = MutableLiveData<Int>()

    private val userRepositoryPageList = ArrayList<UserListRepositoryPage>()
    private val pagesInfo = ArrayList<PageInfo>()
    var selectedPage: PageInfo? = null

    fun performLoadInfo() {
        onShowLoading(true)
        val repoInfo = DataCache.cacheUserRepositoryPageList
        val listSize = repoInfo.size
        val pages = ArrayList<PageInfo>()

        for (index in 0 until listSize) {
            val currentPageInfo = PageInfo(
                index,
                repoInfo[index].page
            )
            pages.add(currentPageInfo)
        }

        userRepositoryPageList.clear()
        userRepositoryPageList.addAll(repoInfo)

        if (DataCache.repoListPageSelected == null) {
            DataCache.repoListPageSelected = pages[0]
            selectSpinnerIndex.postValue(0)
            pageInfoList.postValue(pages)

        } else {
            pageInfoList.postValue(pages)
            selectSpinnerIndex.postValue(DataCache.repoListPageSelected!!.index)
        }

        selectedPage = DataCache.repoListPageSelected!!

        pagesInfo.clear()
        pagesInfo.addAll(pages)
    }

    fun performChangePage(selectedPosition: Int) {
        onShowLoading(true)
        val page = pagesInfo[selectedPosition]
        DataCache.repoListPageSelected = page
        val selectedUserRepoListPage = userRepositoryPageList[page.index].elements
        userRepositoryList.postValue(selectedUserRepoListPage)
    }

    fun onShowLoading(show: Boolean) {
        loading.postValue(show)
    }

    fun resetPageInfo() {
        DataCache.repoListPageSelected = null
    }

}