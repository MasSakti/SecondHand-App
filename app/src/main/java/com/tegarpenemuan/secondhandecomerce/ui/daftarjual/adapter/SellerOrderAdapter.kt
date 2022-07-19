package com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.R
import com.tegarpenemuan.secondhandecomerce.common.ChangeCurrency
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemOrderBinding

class SellerOrderAdapter(
    private val listener: EventListener,
    private var list: List<SellerOrderResponseItem>
) : RecyclerView.Adapter<SellerOrderAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list:  List<SellerOrderResponseItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvBarang.text = item.product_name
        holder.binding.tvHarga.text = ChangeCurrency.gantiRupiah(item.base_price)
        Glide.with(holder.binding.root.context)
            .load(item.Product.image_url)
            .error(R.drawable.ic_launcher_background)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivImg)

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: SellerOrderResponseItem)
    }
}