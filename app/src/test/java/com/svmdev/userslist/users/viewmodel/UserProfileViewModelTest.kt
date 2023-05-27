package com.svmdev.userslist.users.viewmodel


import org.junit.Assert.assertEquals
import org.junit.Test

class UserProfileViewModelTest {

    @Test
    fun performDateFormat() {
        val viewModel = UserProfileViewModel()

        val dateToFormat = "2008-01-07T17:54:22Z"
        val dateExpected = "07/01/2008"

        val result = viewModel.performDateFormat(dateToFormat)

        assertEquals(
            "Datas não correspondem!\ndateExpected: "+dateExpected+"\nresult: "+result,
            dateExpected,
            result
        )
    }
}