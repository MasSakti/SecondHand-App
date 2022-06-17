package com.example.projectgroup2.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projectgroup2.R
import com.example.projectgroup2.databinding.FragmentThirdOnBoardingBinding
import com.example.projectgroup2.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdOnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentThirdOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdOnBoardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGetStarted.setOnClickListener{
            startActivity(
                Intent(activity, LoginActivity::class.java).also{

                }
            )
        }
    }
}