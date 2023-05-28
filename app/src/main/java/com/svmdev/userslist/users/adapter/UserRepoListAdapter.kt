package com.svmdev.userslist.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svmdev.userslist.R
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.repository.service.common.CommonHelper
import com.svmdev.userslist.users.viewmodel.UserReposViewModel

class UserRepoListAdapter(
    private val userRepoList: ArrayList<UserRepository>,
    private val viewModel: UserReposViewModel
) : RecyclerView.Adapter<UserRepoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRepoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_adapter, parent, false)
        return UserRepoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userRepoList.size
    }

    override fun onBindViewHolder(holder: UserRepoListViewHolder, position: Int) {
        val userRepo = userRepoList.get(position)
        holder.tvName.text = userRepo.name
        holder.tvDescription.text = getNotEmptyText(userRepo.description)
        holder.tvCreated.text = CommonHelper.formatDateToString(userRepo.createdAt)
        holder.tvUpdated.text = CommonHelper.formatDateToString(userRepo.updatedAt)

        holder.llCell.setOnClickListener {
            viewModel.repoUrl.postValue(userRepo.htmlURL)
        }
    }

    private fun getNotEmptyText(text: String): String {
        val defaultText = viewModel.appContext.getString(R.string.repo_none)
        return if (text.isNullOrEmpty()) defaultText else text
    }

}