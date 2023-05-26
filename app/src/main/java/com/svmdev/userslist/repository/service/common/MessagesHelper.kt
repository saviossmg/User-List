package com.svmdev.userslist.repository.service.common

import android.content.Context
import android.widget.Toast

object MessagesHelper {

    fun showMessageShort(context: Context, text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    fun showMessageLong(context: Context, text: String) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()


}