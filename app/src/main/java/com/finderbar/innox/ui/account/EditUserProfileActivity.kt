package com.finderbar.innox.ui.account

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityEditUserProfileBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.State
import com.finderbar.innox.repository.TownShip
import com.finderbar.innox.repository.UpdateUser
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class EditUserProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditUserProfileBinding
    private lateinit var acProgress: ACProgressFlower
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_profile)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Edit User Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userName = intent.extras?.get("userName") as String
        val email = intent.extras?.get("email") as String
        val phoneNo = intent.extras?.get("phoneNo") as String
        val address = intent.extras?.get("address") as String
        var stateId = intent.extras?.get("stateId") as Int
        var townShipId = intent.extras?.get("townShipId") as Int
        val stateName = intent.extras?.get("stateName") as String
        val townShipName = intent.extras?.get("townShipName") as String
        val profileImage = intent.extras?.get("profileImage") as String

        binding.txtName.text?.append(userName)
        binding.txtEmail.text?.append(email)
        binding.txtPhone.text?.append(phoneNo)
        binding.txtAddress.text?.append(address)
        binding.dropdownState.text?.append(stateName)
        binding.dropdownTownship.text?.append(townShipName)

        // STATE & DIVISION
        val stateAdaptor = StateArrayAdaptor(this, R.layout.item_dropdown, arrayListOf())
        stateAdaptor.setDropDownViewResource(R.layout.item_dropdown)
        binding.dropdownState.clearFocus();
        binding.dropdownState.setAdapter(stateAdaptor)

        val townshipAdaptor = TownShipArrayAdaptor(this, R.layout.item_dropdown, arrayListOf())
        townshipAdaptor.setDropDownViewResource(R.layout.item_dropdown)
        binding.dropdownTownship.clearFocus();
        binding.dropdownTownship.setAdapter(townshipAdaptor)

        acProgress = ACProgressFlower.Builder(this)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .text("Please Wait")
            .fadeColor(Color.DKGRAY).build();

        bizApiVM.loadState().observe(this, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    stateAdaptor.addAll(res.data?.states!!)
                }
            }
        })

        binding.dropdownState.setOnItemClickListener { parent, _, position, _ ->
            stateId = (parent.getItemAtPosition(position) as State).id
            binding.dropdownTownship.text.clear()
            binding.dropdownTownship.setText("Choose TownShip")
            bizApiVM.loadTownship(stateId!!).observe(this, Observer { res ->
                when (res.status) {
                    Status.SUCCESS -> {
                        townshipAdaptor.addAll(res.data?.townships!!)
                    }
                }
            })
        }

        binding.dropdownTownship.setOnItemClickListener { parent, _, position, _ ->
            townShipId = (parent.getItemAtPosition(position) as TownShip).id
        }

        binding.btnConfirm.setOnClickListener {
            val name = binding.txtName.text.toString()
            val email = binding.txtEmail.text.toString()
            val phoneNo = binding.txtPhone.text.toString()
            val address = binding.txtAddress.text.toString()

            bizApiVM.loadUpdateUserProfile(UpdateUser(name, email, phoneNo, stateId, townShipId, address, profileImage)).observe(this, Observer { res ->
                when(res.status) {
                    Status.LOADING -> {
                        acProgress.show()
                    }
                    Status.SUCCESS -> {
                        acProgress.dismiss()
                        Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
                        val intent = Intent(this, UserProfileActivity::class.java)
                        intent.putExtra("_id", prefs.userId)
                        startActivity(intent)
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