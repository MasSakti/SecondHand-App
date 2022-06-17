package com.example.projectgroup2.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.projectgroup2.R
import com.example.projectgroup2.databinding.FragmentFirstOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstOnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentFirstOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstOnBoardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager>(R.id.vpOnBoarding)
        binding.ibFragmentFirst.setOnClickListener{
            viewPager?.currentItem = 1
        }
        binding.skipFirst.setOnClickListener{
            viewPager?.currentItem = 2
        }
    }
}