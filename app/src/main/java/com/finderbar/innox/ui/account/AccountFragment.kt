package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentAccountBinding
import com.finderbar.innox.prefs
import com.finderbar.innox.replaceFragment
import com.finderbar.innox.viewmodel.BizApiViewModel

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        val context = activity as AppCompatActivity

        if(prefs.authToken.isNotBlank()) {
            context.replaceFragment(UserProfileFragment())
        } else {
            context.replaceFragment(GuestUserFragment())
        }
        return binding.root;
    }

}