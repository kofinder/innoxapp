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
import com.finderbar.innox.repository.User
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.innox.utilities.loadAvatar

class UserProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "User Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val userId: String = intent?.extras?.getString("_id")!!

        bizApiVM.loadUserProfile(userId.toInt()).observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    print("message")
                }
                Status.SUCCESS -> {
                    user = res.data!!
                    binding.imgUser.loadAvatar(Uri.parse(res.data?.image))
                    binding.txtName.text = res.data?.name
                    binding.txtEmail.text = res.data?.email
                    binding.txtPhone.text = res.data?.phoneNo
                    binding.txtDivisionn.text = res.data?.stateName
                    binding.txtTownship.text = res.data?.townshipName
                    binding.txtAddress.text = res.data?.address
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

        binding.btnEdit.setOnClickListener{
            val intent = Intent(this, EditUserProfileActivity::class.java)
            intent.putExtra("userName", user?.name)
            intent.putExtra("email", user?.email)
            intent.putExtra("phoneNo", user?.phoneNo)
            intent.putExtra("address", user?.address)
            intent.putExtra("stateName", user?.stateName)
            intent.putExtra("townShipName", user?.townshipName)
            intent.putExtra("stateId", user?.stateId)
            intent.putExtra("townShipId", user?.townshipId)
            intent.putExtra("profileImage", user?.image)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}