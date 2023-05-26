package com.svmdev.userslist.users.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.svmdev.userslist.R
import com.svmdev.userslist.databinding.ActivityUserListBinding
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.service.common.MessagesHelper
import com.svmdev.userslist.users.adapter.UserListAdapter
import com.svmdev.userslist.users.viewmodel.UserListViewModel

class UserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserListBinding
    private val viewModel = UserListViewModel()

    private lateinit var adapter: UserListAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSplashAnimation()

        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linearLayoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = linearLayoutManager

        setupObservables()
        setupListeners()

        viewModel.initContext(this)
        performLoadUserList()
    }

    private fun onSplashAnimation() {
        installSplashScreen()
    }

    private fun setupObservables() {
        viewModel.loading.observe(this) { loading ->
            onShowLoadElements(loading)
            if (loading) {
                binding.rvUserList.visibility = GONE
                binding.edtxSearch.visibility = GONE
                binding.tvListUnavailable.visibility = GONE
            }
        }

        viewModel.usersList.observe(this) { userList -> onLoadUserList(userList) }
        viewModel.onError.observe(this) { e -> onShowError(e) }
        viewModel.onSearchIsEmpty.observe(this) { isEmpty -> onShowEmptyError(isEmpty) }
    }

    private fun setupListeners() {
        binding.edtxSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(text: Editable?) {
                adapter.onSearchUser(text.toString())
            }
        })
    }

    private fun performLoadUserList() {
        viewModel.loadUserList()
    }

    private fun onLoadUserList(userList: ArrayList<User>) {
        if (userList.isEmpty()) {
            onShowListElements(false)
            onShowErrorTextElements(true, getString(R.string.error_user_list_empty))
        } else {
            adapter = UserListAdapter(userList, viewModel)
            binding.rvUserList.adapter = adapter
            onShowListElements(true)
            onShowErrorTextElements(false, "")
        }
    }

    private fun onShowError(error: Throwable) {
        error.message?.let { MessagesHelper.showMessageShort(this, it) }
        binding.tvListUnavailable.visibility = VISIBLE
        binding.tvListUnavailable.text = getString(R.string.error_api)
    }

    private fun onShowEmptyError(empty: Boolean) {
        if (empty) {
            binding.rvUserList.visibility = GONE
            onShowErrorTextElements(true, getString(R.string.error_user_list_search))
        } else {
            binding.rvUserList.visibility = VISIBLE
            onShowErrorTextElements(false, "")
        }
    }

    private fun onShowLoadElements(show: Boolean) {
        val visibility = if (show) VISIBLE else GONE
        binding.icLoading.root.visibility = visibility
    }

    private fun onShowListElements(show: Boolean) {
        val visibility = if (show) VISIBLE else GONE
        binding.edtxSearch.visibility = visibility
        binding.rvUserList.visibility = visibility
    }

    private fun onShowErrorTextElements(show: Boolean, text: String) {
        val visibility = if (show) VISIBLE else GONE
        binding.tvListUnavailable.visibility = visibility
        binding.tvListUnavailable.text = text
    }
}