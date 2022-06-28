package com.tegarpenemuan.secondhandecomerce.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.data.api.getProduct.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemProductHomeBinding
import okhttp3.internal.filterList
import java.util.jar.Attributes

/**
 * com.tegarpenemuan.secondhandecomerce.ui.home.adapter
 *
 * Created by Tegar Penemuan on 24/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

class ProductAdapter(private val listener: EventListener, private var list: List<GetProductResponse>)
    : RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ListItemProductHomeBinding)
        : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GetProductResponse>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemProductHomeBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        Glide.with(holder.binding.root.context)
            .load(item.image_url)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivImageProduct)
        holder.binding.tvNamaProduct.text = item.name
        holder.binding.tvHargaProduct.text = item.base_price.toString()
        holder.binding.tvJenisProduct.text = item.Categories.joinToString{ it.name }

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: GetProductResponse)
    }
}