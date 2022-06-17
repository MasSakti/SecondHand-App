package com.example.projectgroup2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.projectgroup2.R
import com.example.projectgroup2.databinding.ActivityMainBinding
import com.example.projectgroup2.ui.main.akun.AkunFragment
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment
import com.example.projectgroup2.ui.main.home.HomeFragment
import com.example.projectgroup2.ui.main.jual.JualFragment
import com.example.projectgroup2.ui.main.notif.NotifFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val homeFragment = HomeFragment()
        val notifikasiFragment = NotifFragment()
        val jualFragment = JualFragment()
        val daftarjualFragment = DaftarJualFragment()
        val akunFragment = AkunFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navbar_Home -> setCurrentFragment(homeFragment)
                R.id.navbar_Notifikasi -> setCurrentFragment(notifikasiFragment)
                R.id.navbar_Jual -> setCurrentFragment(jualFragment)
                R.id.navbar_DaftarJual -> setCurrentFragment(daftarjualFragment)
                R.id.navbar_Akun -> setCurrentFragment(akunFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment,fragment)
        commit()
    }
}