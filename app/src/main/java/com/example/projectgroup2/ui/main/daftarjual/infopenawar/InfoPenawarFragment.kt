package com.example.projectgroup2.ui.main.daftarjual.infopenawar

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.projectgroup2.R
import com.example.projectgroup2.data.api.main.approveorder.ApproveOrderRequest
import com.example.projectgroup2.databinding.FragmentInfoPenawarBinding
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.ORDER_ID
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.ORDER_STATUS
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.PRODUCT_BID
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.PRODUCT_BID_DATE
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.PRODUCT_IMAGE
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.PRODUCT_NAME
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.PRODUCT_PRICE
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.USER_CITY
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.USER_IMAGE
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.USER_NAME
import com.example.projectgroup2.ui.main.daftarjual.DaftarJualFragment.Companion.USER_PHONE_NUMBER
import com.example.projectgroup2.utils.convertDate
import com.example.projectgroup2.utils.showToastSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoPenawarFragment : Fragment() {
    private var _binding : FragmentInfoPenawarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoPenawarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoPenawarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        bindView()
    }

    private fun bindViewModel() {
        val bundlePenawar = arguments
        val idOrder = bundlePenawar?.getInt(ORDER_ID)
        val statusOrder = bundlePenawar?.getString(ORDER_STATUS)
        val namaPenawar = bundlePenawar?.getString(USER_NAME)
        val kotaPenawar = bundlePenawar?.getString(USER_CITY)
        val nomorPenawar = bundlePenawar?.getString(USER_PHONE_NUMBER)
        val gambarPenawar = bundlePenawar?.getString(USER_IMAGE)
        val namaProduk = bundlePenawar?.getString(PRODUCT_NAME)
        val hargaAwalProduk = bundlePenawar?.getString(PRODUCT_PRICE)
        val hargaDitawarProduk = bundlePenawar?.getString(PRODUCT_BID)
        val gambarProduk = bundlePenawar?.getString(PRODUCT_IMAGE)
        var status: String

        viewModel.showApproveOrder.observe(viewLifecycleOwner){
            if(it.status == "accepted"){
                binding.groupBtnTolakTerima.visibility = View.GONE
                binding.groupBtnStatusHubungi.visibility = View.VISIBLE
                val bottomFragment = HubungiPenawarFragment(
                    namaPenawar.toString(),
                    kotaPenawar.toString(),
                    nomorPenawar.toString(),
                    gambarPenawar.toString(),
                    namaProduk.toString(),
                    hargaAwalProduk.toString().toInt(),
                    hargaDitawarProduk.toString().toInt(),
                    gambarProduk.toString()
                )
                bottomFragment.show(parentFragmentManager, "Tag")
            }else {
                showToastSuccess(binding.root, "Tawaran $namaPenawar di Tolak!", resources.getColor(R.color.success))
                findNavController().navigate(R.id.action_infoPenawarFragment_to_daftarJualFragment)
            }
        }
    }

    private fun bindView() {
        val bundlePenawar = arguments
        val idOrder = bundlePenawar?.getInt(ORDER_ID)
        val statusOrder = bundlePenawar?.getString(ORDER_STATUS)
        val namaPenawar = bundlePenawar?.getString(USER_NAME)
        val kotaPenawar = bundlePenawar?.getString(USER_CITY)
        val nomorPenawar = bundlePenawar?.getString(USER_PHONE_NUMBER)
        val gambarPenawar = bundlePenawar?.getString(USER_IMAGE)
        val namaProduk = bundlePenawar?.getString(PRODUCT_NAME)
        val hargaAwalProduk = bundlePenawar?.getString(PRODUCT_PRICE)
        val hargaDitawarProduk = bundlePenawar?.getString(PRODUCT_BID)
        val gambarProduk = bundlePenawar?.getString(PRODUCT_IMAGE)
        var status: String

        binding.tvNamaPenawar.text = namaPenawar
        binding.tvKotaPenawar.text = kotaPenawar
        Glide.with(requireContext())
            .load(gambarPenawar)
            .into(binding.ivGambarPenawar)

        binding.tvProductNameTawar.text = namaProduk
        binding.tvProductPriceTawar.text = hargaAwalProduk
        binding.tvProductPriceDitawar.text = hargaDitawarProduk
        binding.tvProductDateTawar.text = convertDate(bundlePenawar?.getString(PRODUCT_BID_DATE).toString())
        Glide.with(requireContext())
            .load(gambarProduk)
            .into(binding.ivProductImageTawar)

        if(statusOrder == "accepted"){
            binding.groupBtnTolakTerima.visibility = View.GONE
            binding.groupBtnStatusHubungi.visibility = View.VISIBLE
        }

        binding.btnTolakTawar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Pesan")
                .setMessage("Tolak Tawaran?")
                .setPositiveButton("Iya"){ positive, _ ->
                    status = "declined"
                    val body = ApproveOrderRequest(
                        status
                    )
                    if (idOrder != null) {
                        viewModel.declineOrder(idOrder, body)
                        positive.dismiss()
                    }
                }
                .setNegativeButton("Tidak"){ negative, _ ->
                    negative.dismiss()
                }
                .show()
        }

        binding.btnTerimaTawar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Pesan")
                .setMessage("Terima Tawaran?")
                .setPositiveButton("Iya"){ positive, _ ->
                    status = "accepted"
                    val body = ApproveOrderRequest(
                        status
                    )
                    if (idOrder != null) {
                        viewModel.declineOrder(idOrder, body)
                        positive.dismiss()
                    }
                }
                .setNegativeButton("Tidak"){ negative, _ ->
                    negative.dismiss()
                }
                .show()
        }

        binding.btnHubungiTawar.setOnClickListener {
            val bottomFragment = HubungiPenawarFragment(
                namaPenawar.toString(),
                kotaPenawar.toString(),
                nomorPenawar.toString(),
                gambarPenawar.toString(),
                namaProduk.toString(),
                hargaAwalProduk.toString().toInt(),
                hargaDitawarProduk.toString().toInt(),
                gambarProduk.toString()
            )
            bottomFragment.show(parentFragmentManager, "Tag")
        }

        binding.btnStatusTawar.setOnClickListener {
            Toast.makeText(requireContext(), "Sabar Adik-adik", Toast.LENGTH_SHORT).show()
        }

    }
}