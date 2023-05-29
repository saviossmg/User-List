package com.svmdev.userslist.repository.data

import com.svmdev.userslist.users.adapter.PageInfo

class DataCache {

    companion object {
        val cacheUsersList = ArrayList<User>()
        var cacheUserProfile: User? = null
        val cacheUserRepositoryList = ArrayList<UserRepository>()

        val repoListUserListRepoPageList = ArrayList<UserListRepositoryPage>()
        val repoListPageInfoList = ArrayList<PageInfo>()
        var repoListPageSelected: PageInfo = PageInfo(0,1)
        var repoListLoaded = false
    }

}