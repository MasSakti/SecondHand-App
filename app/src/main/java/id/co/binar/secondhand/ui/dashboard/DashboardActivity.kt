package id.co.binar.secondhand.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityDashboardBinding
import id.co.binar.secondhand.ui.dashboard.account.AccountFragment
import id.co.binar.secondhand.ui.dashboard.home.HomeFragment
import id.co.binar.secondhand.ui.dashboard.list_sell.ListSellFragment
import id.co.binar.secondhand.ui.dashboard.notification.NotificationFragment
import id.co.binar.secondhand.ui.product_add.ProductAddActivity
import id.co.binar.secondhand.util.DataStoreManager
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(),
    NavigationBarView.OnItemSelectedListener {

    private lateinit var sectionViewPager: DashboardViewPagerAdapter
    private lateinit var binding: ActivityDashboardBinding

    @Inject lateinit var store: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Dashboard"

        val itemFragment = listOf(
            HomeFragment(),
            NotificationFragment(),
            ListSellFragment(),
            AccountFragment()
        )

        sectionViewPager = DashboardViewPagerAdapter(
            supportFragmentManager,
            lifecycle,
            itemFragment
        )

        binding.viewPager.adapter = sectionViewPager
        binding.viewPager.isUserInputEnabled = false
        binding.bottomNavbar.setOnItemSelectedListener(this)
    }

    private fun onSetViewPager(it: Int) {
        binding.viewPager.setCurrentItem(it, false)
        when (binding.viewPager.currentItem) {
            0 -> {
                onSetBottomNavigation(R.id.homeFragment)
                supportActionBar?.title = "Beranda"
            }
            1 -> {
                onSetBottomNavigation(R.id.notificationFragment)
                supportActionBar?.title = "Notifikasi"
            }
            2 -> {
                onSetBottomNavigation(R.id.listSellFragment)
                supportActionBar?.title = "Daftar Jual Saya"
            }
            3 -> {
                onSetBottomNavigation(R.id.accountFragment)
                supportActionBar?.title = "Akun"
            }
            else -> {
                onSetBottomNavigation(R.id.homeFragment)
                supportActionBar?.title = "Beranda"
            }
        }
    }

    private fun onSetBottomNavigation(resId: Int) {
        binding.bottomNavbar.menu.findItem(resId).isChecked = true
    }

    override fun onBackPressed() {
        if (binding.viewPager.currentItem > 0 && binding.viewPager.currentItem <= sectionViewPager.itemCount - 1) {
            onSetViewPager(0)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.homeFragment -> {
                onSetViewPager(0)
                true
            }
            R.id.notificationFragment -> {
                onSetViewPager(1)
                true
            }
            R.id.productAddFragment -> {
                val intent = Intent(this, ProductAddActivity::class.java)
                startActivity(intent)
                false
            }
            R.id.listSellFragment -> {
                onSetViewPager(2)
                true
            }
            R.id.accountFragment -> {
                onSetViewPager(3)
                true
            }
            else -> false
        }
    }
}