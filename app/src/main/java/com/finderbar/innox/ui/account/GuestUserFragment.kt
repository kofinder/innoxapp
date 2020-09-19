package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentGuestUserBinding

class GuestUserFragment: Fragment()  {

    private lateinit var binding: FragmentGuestUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guest_user, container, false)
        binding.btnLogin.setOnClickListener{startActivity(Intent(activity, LoginActivity::class.java))}
        binding.btnRegister.setOnClickListener{startActivity(Intent(activity, RegisterActivity::class.java))}

        return binding.root;
    }

}