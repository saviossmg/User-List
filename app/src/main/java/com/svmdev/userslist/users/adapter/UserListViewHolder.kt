package com.svmdev.userslist.users.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svmdev.userslist.R

class UserListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val llCell: LinearLayout = view.findViewById(R.id.llCell)
    val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
    val tvName: TextView = view.findViewById(R.id.tvName)
    val tvUserType: TextView = view.findViewById(R.id.tvUserType)
    val tvAdmin: TextView = view.findViewById(R.id.tvAdmin)

}