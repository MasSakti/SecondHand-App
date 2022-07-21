package com.tegarpenemuan.secondhandecomerce.ui.editproduct

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tegarpenemuan.secondhandecomerce.databinding.FragmentPilihCategoryBinding
import com.tegarpenemuan.secondhandecomerce.listCategory
import com.tegarpenemuan.secondhandecomerce.listCategoryId
import com.tegarpenemuan.secondhandecomerce.ui.jual.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPilihCategoryFragment(private val update: ()->Unit) : BottomSheetDialogFragment()  {
    private var _binding : FragmentPilihCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProductViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPilihCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnKirimCategory.setOnClickListener {
            viewModel.addCategory(listCategory)
            Handler().postDelayed({
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