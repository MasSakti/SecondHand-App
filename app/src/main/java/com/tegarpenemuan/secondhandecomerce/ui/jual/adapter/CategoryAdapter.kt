package com.tegarpenemuan.secondhandecomerce.ui.jual.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponse
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.ListProductCategoryBinding
import com.tegarpenemuan.secondhandecomerce.utils.listCategory


class CategoryAdapter(
    private val selected: (GetCategoryResponseItem)->Unit,
    private val unselected: (GetCategoryResponseItem)->Unit
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<GetCategoryResponseItem>() {

        override fun areItemsTheSame(
            oldItem: GetCategoryResponseItem,
            newItem: GetCategoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetCategoryResponseItem,
            newItem: GetCategoryResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<GetCategoryResponseItem>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListProductCategoryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ListProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: GetCategoryResponseItem) {
            binding.apply {
                cbCategory.text = data.name
                cbCategory.isChecked = listCategory.contains(data.name)
                cbCategory.setOnClickListener {
                    if (!listCategory.contains(data.name)) {
                        selected.invoke(data)
                    } else {
                        unselected.invoke(data)
                    }
                }
            }
        }
    }
}