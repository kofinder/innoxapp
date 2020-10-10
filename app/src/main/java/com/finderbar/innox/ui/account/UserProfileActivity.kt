package com.finderbar.innox.ui.account
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityUserProfileBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.jovian.utilities.android.loadAvatar

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

        binding.btnEdit.setOnClickListener{
            val editIntent = Intent(this, EditUserProfileActivity::class.java)
            startActivity(editIntent)
        }

        bizApiVM.loadUserProfile(userId.toInt()).observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    print("message")
                }
                Status.SUCCESS -> {
                    binding.imgUser.loadAvatar(Uri.parse(res.data?.image))
                    binding.txtName.text = res.data?.name
                    binding.txtEmail.text = res.data?.email
                    binding.txtPhone.text = res.data?.phoneNo
                    binding.txtDivisionn.text = res.data?.state
                    binding.txtTownship.text = res.data?.township
                    binding.txtAddress.text = res.data?.address
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}