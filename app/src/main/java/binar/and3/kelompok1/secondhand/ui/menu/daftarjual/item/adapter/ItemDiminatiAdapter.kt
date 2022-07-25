package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.common.convertDate
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerOrdersResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemDiminatiBinding
import com.bumptech.glide.Glide

class ItemDiminatiAdapter(
    private val listener: EventListener,
    private var diminati: List<GetSellerOrdersResponse>
) : RecyclerView.Adapter<ItemDiminatiAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: ListItemDiminatiBinding
    ) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataChanged")
    fun updateProductOrder(list: List<GetSellerOrdersResponse>) {
        this.diminati = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemDiminatiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sellerDiminati = diminati[position]

        Glide.with(holder.binding.root.context)
            .load(sellerDiminati.imageProduct)
            .into(holder.binding.ivFotoProduk)
        holder.binding.tvProductName.text = sellerDiminati.productName
        holder.binding.tvBasePrice.text = sellerDiminati.basePrice?.let { currency(it) }
        holder.binding.tvBidPrice.text = "Ditawar: ${sellerDiminati.price?.let { currency(it) }}"
        holder.binding.tvDatetime.text = sellerDiminati.transactionDate?.let { convertDate(it) }

        holder.itemView.setOnClickListener {
            listener.onClick(item = sellerDiminati)
        }
    }

    override fun getItemCount(): Int {
        return diminati.size
    }

    interface EventListener {
        fun onClick(item: GetSellerOrdersResponse)
    }
}