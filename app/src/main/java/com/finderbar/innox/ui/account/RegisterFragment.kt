package com.finderbar.innox.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentRegisterBinding
import com.finderbar.innox.replaceFragment

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as AppCompatActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container , false)

        binding.btnRegister.setOnClickListener{
            context.replaceFragment(RegisterInfoFragment.newInstance(
                binding.name.text.toString(),
                binding.email.text.toString(),
                binding.phone.text.toString(),
                binding.password.text.toString(),
                binding.passwordConfirm.text.toString())
            )
        }

        return binding.root;
    }

}