package com.example.projectgroup2.ui.main.akun

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.projectgroup2.R
import com.example.projectgroup2.databinding.FragmentAkunBinding
import com.example.projectgroup2.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding
    private val viewModel: AkunViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()

        binding.tvLogout.setOnClickListener {
            viewModel.clearCredential()
        }
    }

    private fun bindViewModel() {
        viewModel.showLogin.observe(viewLifecycleOwner){
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}