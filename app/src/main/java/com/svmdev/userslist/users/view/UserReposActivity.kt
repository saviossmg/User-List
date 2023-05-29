package com.svmdev.userslist.users.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.svmdev.userslist.R
import com.svmdev.userslist.databinding.ActivityUserReposBinding
import com.svmdev.userslist.repository.data.UserRepository
import com.svmdev.userslist.repository.service.common.CommonHelper
import com.svmdev.userslist.users.adapter.PageInfo
import com.svmdev.userslist.users.adapter.UserRepoListAdapter
import com.svmdev.userslist.users.viewmodel.UserReposViewModel

class UserReposActivity : BaseActivity() {

    private lateinit var binding: ActivityUserReposBinding
    private var viewModel = UserReposViewModel()

    lateinit var adPagesSpinner: ArrayAdapter<PageInfo>
    private var adapter: UserRepoListAdapter? = null
    private var showMessage = true

    var initializedView = false

    companion object {
        const val REPO_EXTRA_USER = "REPO_EXTRA_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserReposBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrevConfig()
        initBackPressed()
        viewModel.initContext(this)

        setupActionBar()
        setupNavigation()
        setupRepositoryListInfo()
        loadInformation()
    }

    override fun exitOnBackPressed() {
        viewModel.resetPageInfo()
        finish()
        CommonHelper.closeSlideAnimation(this)
    }

    private fun setupActionBar() {
        this.setSupportActionBar(binding.tbHeader)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbHeader.title = getString(
            R.string.repo_list_header_title,
            intent.extras?.getString(REPO_EXTRA_USER)
        )
    }

    private fun setupNavigation() {
        binding.tbHeader.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.spPage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!initializedView)
                    initializedView = true
                else
                    viewModel.performChangePage(position)
            }
        }

        viewModel.repoUrl.observe(this) {
            CommonHelper.openBrowser(it, this)
        }
    }

    private fun setupRepositoryListInfo() {
        viewModel.pageInfoList.observe(this) { pageInfo ->
            adPagesSpinner = ArrayAdapter<PageInfo>(
                this,
                R.layout.spinner_item_text,
                pageInfo
            )
            adPagesSpinner.setDropDownViewResource(R.layout.spinner_select_text)
            binding.spPage.adapter = adPagesSpinner
            binding.spPage.setSelection(viewModel.selectedPage.index, false)
        }

        viewModel.userRepositoryList.observe(this) { repoList -> onLoadUserRepoList(repoList) }
    }

    private fun loadInformation() {
        viewModel.loading.observe(this) { onShowLoadingScreen(it) }

        if(viewModel.isLoadBefore()){
            viewModel.loadInfoByCache()
        } else {
            viewModel.performLoadInfoFirstTime()
        }

    }

    private fun onLoadUserRepoList(userList: ArrayList<UserRepository>) {
        if (userList.isEmpty()) {
            CommonHelper.showMessageShort(this, getString(R.string.error_repo_empty))
        } else {
            if (showMessage) {
                CommonHelper.showMessageLong(this, getString(R.string.repo_message_loaded))
            }
            showMessage = false

            adapter = UserRepoListAdapter(userList, viewModel)
            binding.rvRepoList.adapter = adapter
            binding.rvRepoList.visibility = View.VISIBLE
        }

        viewModel.onShowLoading(false)
    }


    private fun onShowLoadingScreen(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE

        binding.icLoading.tvLoading.text =
            getString(R.string.repo_message_loading, viewModel.selectedPage.page.toString())
        binding.icLoading.root.visibility = visibility
    }


}