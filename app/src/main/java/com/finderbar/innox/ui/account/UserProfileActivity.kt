package com.finderbar.innox.ui.account
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityUserProfileBinding
import com.finderbar.innox.viewmodel.BizApiViewModel

class UserProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "User Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userId: String = intent?.extras?.getString("_id")!!

        bizApiVM.loadUserProfile(userId.toInt()).observe(this, Observer {
            print(it)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}