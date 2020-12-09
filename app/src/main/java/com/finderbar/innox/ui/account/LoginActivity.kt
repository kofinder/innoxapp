package com.finderbar.innox.ui.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityLoginBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.Login
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var acProgress: ACProgressFlower

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        acProgress = ACProgressFlower.Builder(this)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .text("Please Wait")
            .fadeColor(Color.DKGRAY).build();


        binding.btnLogin.setOnClickListener{
            val usrName: String = binding.usrName.text?.trim().toString()
            val password: String = binding.password.text?.trim().toString()
            bizApiVM.loadTokenByLogin(Login(usrName, password)).observe(this, Observer { res ->
                when (res.status) {
                    Status.LOADING -> {
                        acProgress.show()
                    }
                    Status.SUCCESS -> {
                        acProgress.dismiss()
                        Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
                        prefs.authToken = res.data?.jwtToken!!
                        prefs.userId = res.data?.userId!!
                        prefs.userName = res.data?.userName!!
                        prefs.userPhone = res.data?.phoneNo!!
                        prefs.userAvatar = res.data?.avatar!!
                        val homeIntent = Intent(this, MainActivity::class.java)
                        startActivity(homeIntent)
                        finish()
                    }
                    Status.ERROR -> {
                        acProgress.dismiss()
                        Toasty.error(this, res.msg.toString(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}