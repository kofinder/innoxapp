package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.AppConstant.CONFIRM_PASSWORD
import com.finderbar.innox.AppConstant.EMAIL
import com.finderbar.innox.AppConstant.PASSWORD
import com.finderbar.innox.AppConstant.PHONE
import com.finderbar.innox.AppConstant.USER_NAME
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentRegisterInfoBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.Color
import com.finderbar.innox.repository.Register
import com.finderbar.innox.repository.State
import com.finderbar.innox.repository.TownShip
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty

class RegisterInfoFragment: Fragment() {

    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var acProgress: ACProgressFlower

    private var userName: String? = ""
    private var email: String? = ""
    private var phone: String? = ""
    private var password: String? = ""
    private var confirmPassword: String? = ""
    private var stateId: Int? = 0
    private var townShipId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var bundle = savedInstanceState
        if (bundle == null) bundle = arguments
        userName = bundle?.getString(USER_NAME)
        email = bundle?.getString(EMAIL)
        phone = bundle?.getString(PHONE)
        password = bundle?.getString(PASSWORD)
        confirmPassword = bundle?.getString(CONFIRM_PASSWORD)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as AppCompatActivity
        val binding: FragmentRegisterInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_info, container , false)
        acProgress = ACProgressFlower.Builder(context)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(android.graphics.Color.DKGRAY).build();

        bizApiVM.loadState().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val stateAdaptor = StateArrayAdaptor(context, R.layout.item_dropdown, res.data?.states!!)
                    stateAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownState.clearFocus();
                    binding.dropdownState.setAdapter(stateAdaptor)
                    binding.dropdownState.setOnItemClickListener { parent, view, position, id ->
                        stateId = (parent.getItemAtPosition(position) as State).id
                    }
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
                    binding.dropdownTownship.setOnItemClickListener { parent, view, position, id ->
                        townShipId = (parent.getItemAtPosition(position) as TownShip).id
                    }
                }
            }
        })

        binding.btnContinue.setOnClickListener{
            bizApiVM.loadTokenByRegister(Register(userName!!, email!!, phone!!, password!!, confirmPassword!!, stateId!!, townShipId!!, binding.edAddress.text.toString(), "",2)).observe(viewLifecycleOwner, Observer { res ->
                when (res.status) {
                    Status.LOADING -> {
                        acProgress.show()
                    }
                    Status.SUCCESS -> {
                        acProgress.dismiss()
                        Toasty.success(context, "Success!", Toast.LENGTH_SHORT, true).show();
                        prefs.authToken = res.data?.jwtToken!!
                        prefs.userId = res.data?.userId!!
                        startActivity(Intent(context, MainActivity::class.java))
                    }
                    Status.ERROR -> {
                        acProgress.dismiss()
                        Toasty.error(context, res.msg.toString(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            })
        }

        return binding.root;
    }

    companion object {
        fun newInstance(userName: String, email: String, phone: String, password: String, confirmPassword: String): RegisterInfoFragment {
            val fragment = RegisterInfoFragment()
            val args = Bundle()
            args.putString(USER_NAME, userName)
            args.putString(EMAIL, email)
            args.putString(PHONE, phone)
            args.putString(PASSWORD, password)
            args.putString(CONFIRM_PASSWORD, confirmPassword)
            fragment.arguments = args
            return fragment
        }
    }

}