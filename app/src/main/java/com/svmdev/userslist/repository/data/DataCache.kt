package com.svmdev.userslist.repository.data

class DataCache {

    companion object {
        val cacheUsersList = ArrayList<User>()
        val cacheUserRepository = ArrayList<UserRepository>()
        var cacheUserProfile: User? = null
    }

}