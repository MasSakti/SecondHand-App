package com.tegarpenemuan.secondhandecomerce.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityMainBinding
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityOnboardingBinding
import com.tegarpenemuan.secondhandecomerce.databinding.BottomSheetHubungiPenawarBinding
import com.tegarpenemuan.secondhandecomerce.ui.home.HomeFragment
import com.tegarpenemuan.secondhandecomerce.ui.login.Login
import com.tegarpenemuan.secondhandecomerce.ui.onboarding.adapter.PagerAdapterOnBoarding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private var itemList: ArrayList<OnBoardingData> = getItems()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        itemList = getItems()
        binding.viewPagerOnboarding.adapter = PagerAdapterOnBoarding(itemList)
        binding.wormDot.setViewPager2(binding.viewPagerOnboarding)
        binding.viewPagerOnboarding.registerOnPageChangeCallback(pageChangeCallback())
    }

    private fun pageChangeCallback(): ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.imgPrevious.visibility =
                    View.INVISIBLE.takeIf { position == 0 } ?: View.VISIBLE

                if (position == itemList.size - 1) {
                    binding.tvNext.text = "Start"
                    binding.tvSkip.visibility = View.GONE
                } else {
                    binding.tvNext.text = "Next"
                    binding.tvSkip.visibility = View.VISIBLE
                }
            }
        }

    private fun getItems(): ArrayList<OnBoardingData> {
        val items = ArrayList<OnBoardingData>()
        items.add(
            OnBoardingData(
                "Pesan Barang yang Anda Minati", "Beli barang bisa di mana saja dan kapan saja",
                R.drawable.img_onboarding1
            )
        )

        items.add(
            OnBoardingData(
                "Pesan Barang yang Anda Minati", "Beli barang bisa di mana saja dan kapan saja",
                R.drawable.img_onboarding2
            )
        )

        items.add(
            OnBoardingData(
                "Pesan Barang yang Anda Minati", "Beli barang bisa di mana saja dan kapan saja",
                R.drawable.img_onboarding3
            )
        )
        return items
    }

    fun onClick(v: View) {
        when (v) {
            binding.imgPrevious -> {
                val currentItemPosition = binding.viewPagerOnboarding.currentItem
                if (currentItemPosition == 0) return
                binding.viewPagerOnboarding.setCurrentItem(currentItemPosition - 1, true)
            }
            binding.tvNext -> {
                val currentItemPosition = binding.viewPagerOnboarding.currentItem
                if (currentItemPosition == itemList.size - 1) {
                    //Toast.makeText(this, "Start Next Process", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    return
                }
                binding.viewPagerOnboarding.setCurrentItem(currentItemPosition + 1, true)
            }
            binding.tvSkip -> {
                Toast.makeText(this, "Start Next Process", Toast.LENGTH_SHORT).show()
            }
        }
    }
}