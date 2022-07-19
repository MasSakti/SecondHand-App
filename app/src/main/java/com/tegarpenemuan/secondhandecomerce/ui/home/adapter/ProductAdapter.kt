package com.tegarpenemuan.secondhandecomerce.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.currency
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemProductHomeBinding

class ProductAdapter(
    val context: Context,
    private val listener: EventListener,
    private var list: List<GetProductResponse>
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemProductHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GetProductResponse>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        if(item.status == "sold") {
            holder.binding.ivSold.visibility = View.VISIBLE
            holder.itemView.setOnClickListener {
                Toast.makeText(context,"Barang Sudah Terjual",Toast.LENGTH_SHORT).show()
            }
        } else {
            holder.itemView.setOnClickListener {
                listener.onClick(item)
            }
        }
        Glide.with(holder.binding.root.context)
            .load(item.image_url)
            .error(R.drawable.error)
            .placeholder(R.drawable.loading)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivImageProduct)
        holder.binding.tvNamaProduct.text = item.name
        var listCategory :String? = ""
        if (item.Categories.isNotEmpty()) {
            for (data in item.Categories){
                listCategory += ", ${data.name}"
            }
            holder.binding.tvJenisProduct.text = listCategory!!.drop(2)
        }
        holder.binding.tvHargaProduct.text = currency(item.base_price)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: GetProductResponse)
    }
}