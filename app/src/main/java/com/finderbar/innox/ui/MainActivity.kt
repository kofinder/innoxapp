package com.finderbar.innox.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityMainBinding
import com.finderbar.innox.prefs
import com.finderbar.innox.ui.account.LoginActivity
import com.finderbar.innox.ui.account.RegisterActivity
import com.finderbar.jovian.utilities.android.loadAvatar
import com.google.android.material.button.MaterialButton
import de.hdodenhof.circleimageview.CircleImageView
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController(R.id.main_nav_host)
        appBarConfiguration = AppBarConfiguration.Builder(setOf(
            R.id.navigation_home,
            R.id.navigation_instock,
            R.id.navigation_create,
            R.id.navigation_cart,
            R.id.navigation_account
        )).setDrawerLayout(binding.mainDrawerLayout).build()

        setSupportActionBar(binding.mainToolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        visibilityNavElements(navController)

        val header = binding.mainNavigationView.getHeaderView(0)
        val lblUser: ConstraintLayout = header.findViewById(R.id.lbl_user)
        val lblGuest: ConstraintLayout = header.findViewById(R.id.lbl_guest)
        val userProfileImage: CircleImageView = header.findViewById(R.id.avatar)
        val userName: TextView = header.findViewById(R.id.txt_name)
        val userPhone: TextView = header.findViewById(R.id.txt_phone)
        val btnLogin: MaterialButton = header.findViewById(R.id.btn_login)
        val btnRegister: MaterialButton = header.findViewById(R.id.btn_register)

        if(prefs.userId.isNullOrBlank()) {
            lblUser.visibility = View.GONE
            lblGuest.visibility = View.VISIBLE
            val menu = binding.mainBottomNavigationView.menu.getItem(3)
            menu.setIcon(R.drawable.ic_baseline_remove_shopping_cart_24)
            menu.isChecked = true
            menu.setOnMenuItemClickListener {
                Toasty.warning(this, "You are not login!").show()
                true
            }
        } else {
            binding.mainBottomNavigationView.menu.getItem(3).isEnabled = true
            lblGuest.visibility = View.GONE
            lblUser.visibility = View.VISIBLE
            userName.text = prefs.userName
            userPhone.text = prefs.userPhone
            userProfileImage.loadAvatar(Uri.parse(prefs.userAvatar))
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_contact_us -> hideBothNavigation()
                R.id.nav_faq -> hideBottomNavigation()
                else -> showBothNavigation()
            }
        }
    }

    private fun hideBothNavigation() {
        binding.mainBottomNavigationView.visibility = View.GONE
        binding.mainNavigationView.visibility = View.GONE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun hideBottomNavigation() {
        binding.mainBottomNavigationView.visibility = View.GONE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        binding.mainNavigationView.setupWithNavController(navController)
    }

    private fun showBothNavigation() {
        binding.mainBottomNavigationView.visibility = View.VISIBLE
        binding.mainNavigationView.visibility = View.VISIBLE
        binding.mainDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        setupNavControl()
    }

    private fun setupNavControl() {
        binding.mainNavigationView.setupWithNavController(navController)
        binding.mainBottomNavigationView.setupWithNavController(navController)
    }

    fun exitApp() {
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when {
            binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}
