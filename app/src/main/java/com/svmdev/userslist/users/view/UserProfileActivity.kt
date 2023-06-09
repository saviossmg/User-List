package com.svmdev.userslist.users.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.svmdev.userslist.R
import com.svmdev.userslist.databinding.ActivityUserProfileBinding
import com.svmdev.userslist.repository.data.User
import com.svmdev.userslist.repository.data.UserListRepositoryPage
import com.svmdev.userslist.repository.service.common.CommonHelper
import com.svmdev.userslist.users.viewmodel.UserProfileViewModel


class UserProfileActivity : BaseActivity() {

    lateinit var binding: ActivityUserProfileBinding
    private var viewModel = UserProfileViewModel()

    companion object {
        const val USER_EXTRA = "USER_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrevConfig()
        initBackPressed()

        viewModel.initContext(this)

        setupActionBar()
        setupNavigation()
        setupLoad()
        setupProfile()
    }

    override fun exitOnBackPressed() {
        finish()
        CommonHelper.closeSlideAnimation(this)
    }

    private fun setupActionBar() {
        this.setSupportActionBar(binding.tbHeader)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupNavigation() {

        binding.tbHeader.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.ivProfile.setOnClickListener {
            openBrowser(viewModel.getUserProfileUrl())
        }

        binding.tvBlog.setOnClickListener {
            openBrowser(viewModel.getUserBlogUrl())
        }

        binding.btListRepo.setOnClickListener {
            viewModel.performLoadUserRepositoryList()
        }

        viewModel.userListRepositoryPagination.observe(this) { userRepoList ->
            onRepositoryList(userRepoList)
        }
    }

    private fun setupLoad() {
        viewModel.loading.observe(this) { isLoading ->
            val visibility = if (isLoading) VISIBLE else GONE
            binding.icLoading.root.visibility = visibility
        }

        viewModel.loadingMessage.observe(this) { text ->
            binding.icLoading.tvLoading.text = text
        }
    }

    private fun setupProfile() {
        viewModel.selectedUser.observe(this) { onLoadUserInformation(it) }

        viewModel.performSetSelectedUser(intent.extras?.getSerializable(USER_EXTRA) as User)
    }

    private fun onLoadUserInformation(user: User) {
        binding.tbHeader.title = getString(R.string.profile_header_title, user.login)

        Glide
            .with(this)
            .load(user.avatarURL)
            .centerCrop()
            .placeholder(R.drawable.profile_default)
            .into(binding.ivProfile)

        if (textIsEmpty(user.company)) {
            binding.llCompany.visibility = GONE
            binding.dvCompany.root.visibility = GONE
        } else {
            binding.tvCompany.text = user.company
        }

        if (textIsEmpty(user.location)) {
            binding.llLocation.visibility = GONE
            binding.dvLocation.root.visibility = GONE
        } else {
            binding.tvLocation.text = user.location
        }

        if (textIsEmpty(user.blog)) {
            binding.llBlog.visibility = GONE
            binding.dvBlog.root.visibility = GONE
        } else {
            binding.tvBlog.text = user.blog
        }

        binding.tvName.text = user.name
        binding.tvCreated.text = viewModel.performDateFormat(user.createdAt)
        binding.tvUpdate.text = viewModel.performDateFormat(user.updatedAt)
    }

    private fun onRepositoryList(userRepoList: ArrayList<UserListRepositoryPage>) {
        if (userRepoList.isNotEmpty()) {
            val repoListIntent = Intent(this, UserReposActivity::class.java)
            repoListIntent.putExtra(UserReposActivity.REPO_EXTRA_USER, viewModel.userLogin)
            startActivity(repoListIntent)
            CommonHelper.openSlideAnimation(this)
        } else {
            CommonHelper.showMessageLong(this, getString(R.string.error_repo_empty))
        }
    }

    private fun openBrowser(url: String) {
        CommonHelper.openBrowser(url, this)
    }

    private fun textIsEmpty(text: String): Boolean {
        return text.isNullOrBlank() || text.isEmpty()
    }

}