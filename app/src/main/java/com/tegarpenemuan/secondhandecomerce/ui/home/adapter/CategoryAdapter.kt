package com.tegarpenemuan.secondhandecomerce.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponse
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getProduct.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemCategoryHomeBinding
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemProductHomeBinding

/**
 * com.tegarpenemuan.secondhandecomerce.ui.home.adapter
 *
 * Created by Tegar Penemuan on 24/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

class CategoryAdapter(private val listener: EventListener, private var list: List<GetCategoryResponseItem>)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ListItemCategoryHomeBinding)
        : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GetCategoryResponseItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemCategoryHomeBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.txtCategory.text = item.name



        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: GetCategoryResponseItem)
    }
}