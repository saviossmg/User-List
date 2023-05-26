package com.svmdev.userslist.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.SortedListAdapterCallback
import com.bumptech.glide.Glide
import com.svmdev.userslist.R
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.common.MessagesHelper
import com.svmdev.userslist.users.viewmodel.UserListViewModel

class UserListAdapter(
    private val userList: ArrayList<User>,
    private val viewModel: UserListViewModel
) : RecyclerView.Adapter<UserListViewHolder>() {

    private var sUserList: SortedList<User> =
        SortedList(User::class.java, object : SortedListAdapterCallback<User>(this) {

            override fun compare(o1: User, o2: User): Int {
                return o1.login.compareTo(o2.login)
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.login == newItem.login
            }

            override fun areItemsTheSame(item1: User, item2: User): Boolean {
                return item1 == item2
            }
        })

    init {
        sUserList.addAll(userList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_adapter, parent, false)
        return UserListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sUserList.size()
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = sUserList.get(position)

        holder.tvName.text = user.login
        holder.tvUserType.text = getUserType(user)
        holder.tvAdmin.text = isAdminUser(user)

        Glide
            .with(viewModel.appContext)
            .load(user.avatarURL)
            .centerCrop()
            .placeholder(R.drawable.profile_default)
            .into(holder.ivProfile);

        holder.llCell.setOnClickListener {
            viewModel.selectedUser.postValue(user)
        }
    }

    fun onSearchUser(text: String) {
        val textSearch = text.uppercase()
        val filteredList = ArrayList<User>()

        if(text.isEmpty()){
            filteredList.addAll(userList)
        } else {
            userList.forEach {
                val loginText = it.login.uppercase()
                if (loginText.contains(textSearch)) filteredList.add(it)
            }
        }

        viewModel.onSearchIsEmpty.postValue(filteredList.isEmpty())

        sUserList.beginBatchedUpdates()
        sUserList.clear()
        sUserList.addAll(filteredList)
        sUserList.endBatchedUpdates()
    }

    private fun getUserType(user: User): String {
        return viewModel.appContext.getString(R.string.user_type, user.type)
    }

    private fun isAdminUser(user: User): String {
        val yesOrNo = if(user.siteAdmin) R.string.yes else R.string.no
        val adminAnswers = viewModel.appContext.getString(yesOrNo)
        return viewModel.appContext.getString(R.string.user_admin, adminAnswers)
    }

}