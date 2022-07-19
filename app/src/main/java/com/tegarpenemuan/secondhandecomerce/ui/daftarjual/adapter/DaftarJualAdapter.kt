package com.tegarpenemuan.secondhandecomerce.ui.daftarjual.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.common.ChangeCurrency
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemDaftarJualBinding

class DaftarJualAdapter(
    private val listener: EventListener,
    private var list: List<GetProductResponse>
) : RecyclerView.Adapter<DaftarJualAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemDaftarJualBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GetProductResponse>) {
        this.list = list.sortedBy { it.name }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDaftarJualBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvNamaProduct.text = item.name
        Glide.with(holder.binding.root.context)
            .load(item.image_url)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivImageProduct)
        holder.binding.tvHargaProduct.text = ChangeCurrency.gantiRupiah(item.base_price.toString())

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