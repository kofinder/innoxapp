package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentAccountBinding
import com.finderbar.innox.viewmodel.BaseApiViewModel


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.btnLogin.setOnClickListener{startActivity(Intent(activity, LoginActivity::class.java))}
        binding.btnRegister.setOnClickListener{startActivity(Intent(activity, RegisterActivity::class.java))}
        return binding.root;
    }

}