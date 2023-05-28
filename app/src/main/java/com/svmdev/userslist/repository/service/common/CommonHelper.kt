package com.svmdev.userslist.repository.service.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.text.SimpleDateFormat

object CommonHelper {

    fun showMessageShort(context: Context, text: String) = Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

    fun showMessageLong(context: Context, text: String) = Toast.makeText(context, text, Toast.LENGTH_LONG).show()

    fun formatDateToString(dateStr: String): String? {
        val dateSubStr = dateStr.split("T")
        val apiDateFormatPattern = "yyyy-MM-dd"
        val apiDate = SimpleDateFormat(apiDateFormatPattern).parse(dateSubStr[0]);

        val showDatePattern = SimpleDateFormat("dd/MM/yyyy")

        return apiDate?.let { showDatePattern.format(it) }
    }

    fun openBrowser(url: String, ctx: Activity) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        ctx.startActivity(openURL)
    }



}