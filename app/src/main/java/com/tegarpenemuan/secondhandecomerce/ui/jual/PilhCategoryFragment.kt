package com.tegarpenemuan.secondhandecomerce.ui.jual

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentPilhCategoryBinding
import com.tegarpenemuan.secondhandecomerce.ui.jual.adapter.CategoryAdapter
import com.tegarpenemuan.secondhandecomerce.utils.listCategory
import com.tegarpenemuan.secondhandecomerce.utils.listCategoryId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PilihCategoryFragment(private val update: ()->Unit) : BottomSheetDialogFragment()  {
    private var _binding : FragmentPilhCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JualViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPilhCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please Wait...")
        binding.btnKirimCategory.setOnClickListener {
            viewModel.addCategory(listCategory)
            Handler().postDelayed({
                progressDialog.dismiss()
                update.invoke()
                dismiss()
            }, 1000)
        }
        viewModel.getCategory()

        viewModel.showCategory.observe(viewLifecycleOwner){
            categoryAdapter.submitData(it)
        }

        categoryAdapter = CategoryAdapter(
            selected = { selected ->
                listCategory.add(selected.name)
                listCategoryId.add(selected.id)
            },
            unselected = { unselected ->
                listCategory.remove(unselected.name)
                listCategoryId.remove(unselected.id)
            }
        )
        binding.rvPilihCategory.adapter = categoryAdapter
    }
}