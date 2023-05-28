package com.svmdev.userslist.users.adapter

import android.view.View
import android.widget.LinearLayout

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svmdev.userslist.R

class UserRepoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val llCell: LinearLayout = view.findViewById(R.id.llRepoCell)
    val tvName: TextView = view.findViewById(R.id.tvRepoName)
    val tvDescription: TextView = view.findViewById(R.id.tvRepoDescription)
    val tvCreated: TextView = view.findViewById(R.id.tvRepoCreated)
    val tvUpdated: TextView = view.findViewById(R.id.tvRepoUpdated)

}