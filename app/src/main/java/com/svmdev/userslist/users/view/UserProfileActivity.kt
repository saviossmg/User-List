package com.svmdev.userslist.users.view

import android.os.Bundle
import com.svmdev.userslist.databinding.ActivityUserProfileBinding
import com.svmdev.userslist.repository.data.User

class UserProfileActivity : BaseActivity() {

    lateinit var teste: User
    lateinit var binding: ActivityUserProfileBinding

    companion object {
        const val USER_EXTRA = "USER_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPrevConfig()

        teste = intent.extras?.getSerializable(USER_EXTRA) as User

        setupActionBar()
        setupNavigation()
    }

    private fun setupActionBar(){
        this.setSupportActionBar(binding.tbHeader)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tbHeader.title = "Perfil de " + teste.login

    }

    private fun setupNavigation() {
        binding.tbHeader.setNavigationOnClickListener {
            onBackPressed()
        }

    }


}