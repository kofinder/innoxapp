package com.finderbar.innox.ui.account

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityEditUserProfileBinding
import com.finderbar.innox.viewmodel.BizApiViewModel

class EditUserProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditUserProfileBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_profile)
        supportActionBar?.title = "User Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}