package com.svmdev.userslist.repository.data

import com.svmdev.userslist.users.adapter.PageInfo

class DataCache {

    companion object {
        val cacheUsersList = ArrayList<User>()
        var cacheUserProfile: User? = null
        val cacheUserRepositoryList = ArrayList<UserRepository>()
        val cacheUserRepositoryPageList = ArrayList<UserListRepositoryPage>()

        var repoListPageSelected: PageInfo? = null
    }

}