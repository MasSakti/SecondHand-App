@file:Suppress("DEPRECATION")

package binar.and3.kelompok1.secondhand.ui.menu.jual.bottomsheets

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.common.listCategory
import binar.and3.kelompok1.secondhand.common.listCategoryId
import binar.and3.kelompok1.secondhand.databinding.FragmentCategoryBottomSheetsBinding
import binar.and3.kelompok1.secondhand.ui.menu.jual.JualViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryBottomSheetsFragment(private val update: () -> Unit) : BottomSheetDialogFragment() {

    private var _binding : FragmentCategoryBottomSheetsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JualViewModel by viewModels()
    private lateinit var categoryBottomSheetsAdapter: CategoryBottomSheetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCategoryBottomSheetsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryBottomSheetsAdapter = CategoryBottomSheetsAdapter(
            selected = { selected ->
                selected.name?.let { listCategory.add(it) }
                selected.id?.let { listCategoryId.add(it) }
            },
            unselected = { unselected ->
                listCategory.remove(unselected.name)
                listCategoryId.remove(unselected.id)
            }
        )

        viewModel.getSellerCategory()
        binding.rvCategories.adapter = categoryBottomSheetsAdapter

        bindView()
        bindViewModel()
    }

    private fun bindView() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Tunggu Sebentar...")

        binding.btnPilih.setOnClickListener {
            viewModel.addCategory(listCategory)
            Handler().postDelayed({
                progressDialog.dismiss()
                update.invoke()
                dismiss()
            }, 1000)
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowCategory.observe(viewLifecycleOwner) {
            categoryBottomSheetsAdapter.submitData(it)
        }
    }

}