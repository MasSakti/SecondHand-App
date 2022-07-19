package com.tegarpenemuan.secondhandecomerce.ui.jual

import android.app.ActionBar
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentJualBinding
import com.tegarpenemuan.secondhandecomerce.listCategory
import com.tegarpenemuan.secondhandecomerce.listCategoryId
import com.tegarpenemuan.secondhandecomerce.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class JualFragment : Fragment() {
    private var _binding: FragmentJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JualViewModel by viewModels()

    private var uri: String = ""
    companion object {
        const val NAMA_PRODUCT_KEY = "namaProduk"
        const val HARGA_PRODUCT_KEY = "hargaProduk"
        const val DESKRIPSI_PRODUCT_KEY = "deskripsiProduk"
        const val KATEGORI_PRODUCT_KEY = "kategoriProduk"
        const val ALAMAT_PRODUCT_KEY = "alamatProduk"
        const val IMAGE_PRODUCT_KEY = "imageProduk"
//        const val NAME_USER_KEY = "userName"
//        const val ADDRESS_USER_KEY = "userAlamat"
//        const val IMAGE_USER_KEY = "userImage"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJualBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        bindViewModel()
        bindView()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
//                findNavController().navigate(R.id.action_jualFragment_to_homeFragment)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    private fun bindViewModel(){
        viewModel.categoryList.observe(viewLifecycleOwner){ kat ->
            if (kat.isNotEmpty()){
                var kategori = ""
                for(element in kat){
                    kategori += ", $element"
                }
                binding.etKategori.setText(kategori.drop(2))
            }
        }

        viewModel.showSuccess.observe(viewLifecycleOwner){
            showToastSuccess()
//            findNavController().navigate(R.id.action_jualFragment_to_daftarJualFragment)
        }
    }

    private fun bindView(){
        binding.etKategori.setOnClickListener {
            val bottomFragment = PilihCategoryFragment(
                update = {
                    viewModel.addCategory(listCategory)
                })
            bottomFragment.show(parentFragmentManager, "Tag")
        }

        binding.ivFoto.setOnClickListener {
            openImagePicker()
        }

        val bundle = Bundle()
        binding.btnPreview.setOnClickListener {
            resetError()
            val namaProduk = binding.etNama.text.toString()
            val hargaProduk = binding.etHarga.text.toString()
            val deskripsiProduk = binding.etDeskripsi.text.toString()
            val kategoriProduk = binding.etKategori.text.toString()
            val alamatPenjual = binding.etLocation.text.toString()
            val validation = validation(
                namaProduk,
                hargaProduk,
                deskripsiProduk,
                alamatPenjual,
                uri,
                listCategoryId
            )
            if (validation == "passed") {
                bundle.putString(NAMA_PRODUCT_KEY, namaProduk)
                bundle.putString(HARGA_PRODUCT_KEY, hargaProduk)
                bundle.putString(DESKRIPSI_PRODUCT_KEY, deskripsiProduk)
                bundle.putString(KATEGORI_PRODUCT_KEY, kategoriProduk)
                bundle.putString(ALAMAT_PRODUCT_KEY, alamatPenjual)
                bundle.putString(IMAGE_PRODUCT_KEY, uri)
                findNavController().navigate(R.id.action_navigation_jual_to_previewFragment2, bundle)
            }
        }

        binding.btnTerbitkan.setOnClickListener {
            resetError()
            val namaProduk = binding.etNama.text.toString()
            val hargaProduk = binding.etHarga.text.toString()
            val deskripsiProduk = binding.etDeskripsi.text.toString()
            val alamatPenjual = binding.etLocation.text.toString()
            val validation = validation(
                namaProduk,
                hargaProduk,
                deskripsiProduk,
                alamatPenjual,
                uri,
                listCategoryId
            )
            if (validation == "passed") {
                viewModel.uploadProduct(
                    namaProduk,
                    deskripsiProduk,
                    hargaProduk,
                    listCategoryId,
                    alamatPenjual,
                    uriToFile(Uri.parse(uri), requireContext())
                )
            }
        }
    }

    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivFoto)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data
                    uri = fileUri.toString()
                    if (fileUri != null) {
                        loadImage(fileUri)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {

                }
            }
        }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    requireContext().externalCacheDir,
                    "ImagePicker"
                )
            ) //Crop image(Optional), Check Customization for more option
            .compress(1024) //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    fun validation(namaProduk: String, hargaProduk: String, deskripsiProduk: String, alamatPenjual: String, uriFoto: String, listCategory: List<Int>): String {
        when {
            namaProduk.isEmpty() -> {
                binding.etNama.error = "Nama Produk tidak boleh kosong"
                return "Nama Produk Kosong!"
            }
            hargaProduk.isEmpty() -> {
                binding.etHarga.error = "Harga Produk tidak boleh kosong"
                return "Harga Produk Kosong!"
            }
            hargaProduk.toInt() > 2000000000 -> {
                binding.etHarga.error = "Harga Produk tidak boleh lebih dari 2M"
                return "Harga Produk Melebihi Batas!"
            }
            deskripsiProduk.isEmpty() -> {
                binding.etDeskripsi.error = "Nama Produk tidak boleh kosong"
                return "Deskripsi Produk Kosong!"
            }
            alamatPenjual.isEmpty() -> {
                binding.etLocation.error = "alamat Produk tidak boleh kosong"
                return "Deskripsi Produk Kosong!"
            }
            uriFoto.isEmpty() -> {
                Toast.makeText(requireContext(), "Foto Produk Kosong", Toast.LENGTH_SHORT).show()
                return "Foto Produk Kosong!"
            }
            listCategory.isEmpty() -> {
                binding.etKategori.error = "Kategori produk tidak boleh kosong"
                Toast.makeText(requireContext(), "Kategori Produk Kosong", Toast.LENGTH_SHORT).show()
                return "Kategori Produk Kosong!"
            }
            else -> {
                return "passed"
            }
        }
    }

    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(binding.root, "Produk berhasil di terbitkan.", Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_close_24, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(resources.getColor(R.color.success))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

    fun resetError() {
        binding.etNama.error = null
        binding.etHarga.error = null
        binding.etKategori.error = null
        binding.etDeskripsi.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}