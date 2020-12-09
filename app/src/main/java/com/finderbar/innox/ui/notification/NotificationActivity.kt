package com.finderbar.innox.ui.notification

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityNotificationBinding
import com.finderbar.innox.viewmodel.BizApiViewModel
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class NotificationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Notification"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}