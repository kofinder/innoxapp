package com.finderbar.innox.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.finderbar.innox.R
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.jovian.utilities.android.loadAvatar
import com.finderbar.jovian.utilities.android.loadLarge
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    @BindView(R.id.main_drawer_layout) lateinit var drawerLayout: DrawerLayout
    @BindView(R.id.main_bottom_navigation_view) lateinit var bottomNav: BottomNavigationView
    @BindView(R.id.main_navigation_view) lateinit var navView : NavigationView
    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.finderbar.innox.R.layout.activity_main)
        ButterKnife.bind(this)

        navController = findNavController(com.finderbar.innox.R.id.main_nav_host)
        appBarConfiguration = AppBarConfiguration.Builder(setOf(
            R.id.navigation_home,
            R.id.navigation_instock,
            R.id.navigation_create,
            R.id.navigation_cart,
            R.id.navigation_account
        )).setDrawerLayout(drawerLayout).build();

        setSupportActionBar(toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        visibilityNavElements(navController)
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_credit -> hideBothNavigation()
                R.id.nav_privacy_policy -> hideBottomNavigation()
                else -> showBothNavigation()
            }
        }
    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        bottomNav?.visibility = View.GONE
        navView.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        bottomNav.visibility = View.GONE
        navView.visibility = View.VISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer
        navView.setupWithNavController(navController) //Setup Drawer navigation with navController
    }

    private fun showBothNavigation() {
        bottomNav.visibility = View.VISIBLE
        navView.visibility = View.VISIBLE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl()
    }

    private fun setupNavControl() {
        navView.setupWithNavController(navController)
        bottomNav.setupWithNavController(navController)
    }

    fun exitApp() {
        this.finishAffinity()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
}
