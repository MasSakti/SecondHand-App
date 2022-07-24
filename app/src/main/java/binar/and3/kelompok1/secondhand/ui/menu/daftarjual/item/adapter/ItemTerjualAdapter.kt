package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerOrdersResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemDiminatiBinding
import com.bumptech.glide.Glide

class ItemTerjualAdapter(
    private val listener: EventListener,
    private var terjual: List<GetSellerOrdersResponse>
) : RecyclerView.Adapter<ItemTerjualAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemDiminatiBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataChanged")
    fun updateProductOrder(list: List<GetSellerOrdersResponse>) {
        this.terjual = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDiminatiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sellerTerjual = terjual[position]

        Glide.with(holder.binding.root.context)
            .load(sellerTerjual.imageProduct)
            .into(holder.binding.ivFotoProduk)
        holder.binding.tvProductName.text = sellerTerjual.productName
        holder.binding.tvBasePrice.text = sellerTerjual.basePrice?.let { currency(it) }
        holder.binding.tvBasePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.tvBidPrice.text = "Ditawar: ${sellerTerjual.price?.let { currency(it) }}"
        holder.binding.tvDatetime.text = sellerTerjual.transactionDate

        holder.itemView.setOnClickListener {
            listener.onClick(item = sellerTerjual)
        }
    }

    override fun getItemCount(): Int {
        return terjual.size
    }

    interface EventListener {
        fun onClick(item: GetSellerOrdersResponse)
    }
}