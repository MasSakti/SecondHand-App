package id.co.binar.secondhand.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.EntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_dashboard) as NavHostFragment
        navController = navHost.navController
        val appBar = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.notificationFragment,
                R.id.listSellFragment,
                R.id.accountFragment
            )
        )
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBar)
        binding.bottomNavbar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}