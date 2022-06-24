package com.tegarpenemuan.secondhandecomerce.ui.jual.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityLoginBinding.inflate
import com.tegarpenemuan.secondhandecomerce.databinding.ActivityMainBinding.inflate
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentJualBinding
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentSeller30Binding
import com.tegarpenemuan.secondhandecomerce.ui.register.Register

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Seller30.newInstance] factory method to
 * create an instance of this fragment.
 */
class Seller30 : Fragment() {
    private var _binding: FragmentSeller30Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeller30Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHubungi.setOnClickListener{
            Toast.makeText(context, "Hubungi Wa", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}