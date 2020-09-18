package com.finderbar.innox.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.finderbar.innox.AppConstant.EMAIL
import com.finderbar.innox.AppConstant.PASSWORD
import com.finderbar.innox.AppConstant.PHONE
import com.finderbar.innox.AppConstant.USER_NAME
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentRegisterInfoBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.viewmodel.BizApiViewModel

class RegisterInfoFragment: Fragment() {

    private lateinit var binding: FragmentRegisterInfoBinding
    private val bizApiVM: BizApiViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as AppCompatActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_info, container , false)

        bizApiVM.loadState().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val stateAdaptor = StateArrayAdaptor(context, R.layout.item_dropdown, res.data?.states!!)
                    stateAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownState.clearFocus();
                    binding.dropdownState.setAdapter(stateAdaptor)
                }
            }
        })

        bizApiVM.loadTownship(1).observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val townshipAdaptor = TownShipArrayAdaptor(context, R.layout.item_dropdown, res.data?.townships!!)
                    townshipAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownTownship.clearFocus();
                    binding.dropdownTownship.setAdapter(townshipAdaptor)
                }
            }
        })

        return binding.root;
    }

    companion object {
        fun newInstance(name: String, email: String, phone: String, password: String): RegisterInfoFragment {
            val fragment = RegisterInfoFragment()
            val args = Bundle()
            args.putString(USER_NAME, name)
            args.putString(EMAIL, email)
            args.putString(PHONE, phone)
            args.putString(PASSWORD, password)
            fragment.arguments = args
            return fragment
        }
    }

}