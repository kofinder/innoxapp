package com.finderbar.innox.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.finderbar.innox.AppContext
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentUserProfileBinding
import com.finderbar.innox.prefs
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.ui.notification.NotificationActivity
import com.finderbar.innox.ui.order.OrderActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.innox.utilities.loadAvatar
import es.dmoral.toasty.Toasty

class UserProfileFragment: Fragment()  {

    private lateinit var binding: FragmentUserProfileBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = AppContext;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        binding.txtName.text = prefs.userName
        binding.avatar.loadAvatar(Uri.parse(prefs.userAvatar))

        binding.lblAccount.setOnClickListener{
            val userProfile = Intent(context, UserProfileActivity::class.java)
            userProfile.putExtra("_id", prefs.userId)
            startActivity(userProfile)
        }

        binding.lblOrder.setOnClickListener{
            val order = Intent(context, OrderActivity::class.java)
            order.putExtra("_id", prefs.userId)
            startActivity(order)
        }

        binding.lblNotification.setOnClickListener{
            val notification = Intent(context, NotificationActivity::class.java)
            notification.putExtra("_id", prefs.userId)
            startActivity(notification)
        }

        binding.lblExit.setOnClickListener{
            prefs.logout()
            Toasty.success(AppContext, "Successfully Logout!").show()
            val homeIntent = Intent(context, MainActivity::class.java)
            startActivity(homeIntent)
        }

        return binding.root;
    }
}
