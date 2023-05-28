package com.svmdev.userslist.users.adapter

data class PageInfo(val index: Int, val page: Int) {
    override fun toString(): String {
        return "Página $page"
    }
}