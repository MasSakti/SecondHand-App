package binar.and3.kelompok1.secondhand.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.databinding.ActivityMainBinding
import binar.and3.kelompok1.secondhand.ui.menu.akun.AkunFragment
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.DaftarFragment
import binar.and3.kelompok1.secondhand.ui.menu.home.HomeFragment
import binar.and3.kelompok1.secondhand.ui.menu.notifikasi.NotifikasiFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = HomeFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifikasi-> {
                val fragment = NotifikasiFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_daftar-> {
                val fragment = DaftarFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_akun-> {
                val fragment = AkunFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_home, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}