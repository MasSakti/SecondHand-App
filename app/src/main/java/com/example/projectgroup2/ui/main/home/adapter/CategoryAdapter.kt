package com.example.projectgroup2.ui.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponseItem
import com.example.projectgroup2.data.api.main.category.CategoryResponse
import com.example.projectgroup2.databinding.ListCategoryHomeBinding
import com.example.projectgroup2.databinding.ListProductHomeBinding

class CategoryAdapter(private val onClick: OnClickListener): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val diffCallBack = object: DiffUtil.ItemCallback<CategoryResponse>(){
        override fun areItemsTheSame(oldItem: CategoryResponse, newItem: CategoryResponse): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: CategoryResponse, newItem: CategoryResponse): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value: List<CategoryResponse>?) = differ.submitList(value)

    interface OnClickListener {
        fun onClickItem (data: CategoryResponse)
    }

    inner class ViewHolder(private val binding: ListCategoryHomeBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind (data: CategoryResponse){
            binding.tvCategoryHome.text = data.name
            binding.root.setOnClickListener {
                onClick.onClickItem(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListCategoryHomeBinding.inflate(inflate,parent,false))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}