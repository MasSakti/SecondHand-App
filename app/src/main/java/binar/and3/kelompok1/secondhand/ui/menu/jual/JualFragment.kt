package binar.and3.kelompok1.secondhand.ui.menu.jual

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.common.listCategory
import binar.and3.kelompok1.secondhand.common.listCategoryId
import binar.and3.kelompok1.secondhand.common.uriToFile
import binar.and3.kelompok1.secondhand.databinding.FragmentJualBinding
import binar.and3.kelompok1.secondhand.ui.menu.jual.bottomsheets.CategoryBottomSheetsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class JualFragment : Fragment() {

    private var _binding: FragmentJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JualViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    private var uri: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.WHITE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJualBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bindView()
        bindViewModel()
        viewModel.getSellerCategory()

        return root
    }

    private fun bindView() {
        binding.etNamaProduk.doAfterTextChanged {
            viewModel.onChangeName(it.toString())
        }
        binding.etDeskripsi.doAfterTextChanged {
            viewModel.onChangeDescription(it.toString())
        }
        binding.etHargaProduk.doAfterTextChanged {
            viewModel.onChangeBasePrice(it.hashCode())
        }
        binding.etKategori.doAfterTextChanged {
            viewModel.onChangeCategoryIds(listCategoryId)
        }
        binding.etKategori.setOnClickListener {
            val bottomFragment = CategoryBottomSheetsFragment(
                update = {
                    viewModel.addCategory(listCategory)
                })
            bottomFragment.show(parentFragmentManager, "Tag")
        }
        binding.ivFotoProduk.setOnClickListener {
            openImagePicker()
        }
        binding.btnTerbitkan.setOnClickListener {
            viewModel.onValidate()
        }
    }

    private fun bindViewModel() {
        viewModel.categoryList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                var category = ""
                for (element in it) {
                    category += ", $element"
                }
                binding.etKategori.setText(category.drop(2))
            }
        }
    }

    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivFotoProduk)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data
                    uri = fileUri.toString()
                    if (fileUri != null) {
                        loadImage(fileUri)
                        viewModel.onChangeImage(uriToFile(Uri.parse(uri), requireContext()))
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: + ${ImagePicker.getError(data)}",
                        Toast.LENGTH_SHORT
                    ).show()
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
            )
            .compress(1024) // 1MB
            .maxResultSize(
                720, 720
            )
            .createIntent {
                startForProfileImageResult.launch(it)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}