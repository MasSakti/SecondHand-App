package com.tegarpenemuan.secondhandecomerce.ui.notifications.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tegarpenemuan.secondhandecomerce.convertDate
import com.tegarpenemuan.secondhandecomerce.currency
import com.tegarpenemuan.secondhandecomerce.data.api.Notification.GetNotification.GetNotifResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.ListItemNotificationsBinding
import com.tegarpenemuan.secondhandecomerce.striketroughtText

class NotificationsAdapter(
    private val listener: EventListener,
    private var list: List<GetNotifResponseItem>
) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemNotificationsBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GetNotifResponseItem>) {
        this.list = list.sortedByDescending { it.createdAt }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemNotificationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvProdukInfo.text = item.product_name
        Glide.with(holder.binding.root.context)
            .load(item.image_url)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivGambarProduk)
        holder.binding.tvTanggalNotification.text = convertDate(item.createdAt)

        val status = item.status
        for (i in list) {
            when(status) {
                "bid" -> {
                    holder.binding.tvNotificationInfo.text = "Penawaran Produk"
                    holder.binding.tvHargaInfo.text = "Harga Asli ${currency(item.base_price.toInt())}"
                    holder.binding.tvAktivitasInfo.text = "Ditawar ${currency(item.bid_price)}"
                    holder.itemView.setOnClickListener {
                        listener.onClickAcc(item)
                    }
                }
                "create" -> {
                    holder.binding.tvNotificationInfo.text = "Berhasil Diterbitkan"
                    holder.binding.tvHargaInfo.text = "Harga Asli ${currency(item.base_price.toInt())}"
                    holder.itemView.setOnClickListener {
                        listener.onClick(item)
                    }
                }
                "accepted" -> {
                    holder.binding.tvNotificationInfo.text = "Penawaran Telah Diterima"
                    striketroughtText(holder.binding.tvHargaInfo,"Harga Asli ${currency(item.base_price.toInt())}")
                    holder.binding.tvAktivitasInfo.text = "Berhasil Ditawar ${currency(item.bid_price)}"
                    holder.binding.tvSpesifikAktivitasInfo.text = "Kamu akan segera dihubungi penjual via Whatsapp"
                    holder.itemView.setOnClickListener {
                        listener.onClickAcc(item)
                    }
                }
                "declined" -> {
                    holder.binding.tvAktivitasInfo.text = "Penawaran Ditolak"
                    holder.binding.tvHargaInfo.text = "Harga Asli ${currency(item.base_price.toInt())}"
                    holder.binding.tvNotificationInfo.text = "Tawaran Ditolak ${currency(item.bid_price)}"
                    holder.itemView.setOnClickListener {
                        listener.onClickAcc(item)
                    }
                }
            }
        }

        val data = item.read
        for (i in list) {
            when(data) {
                true -> holder.binding.ivNotification.visibility = View.GONE
                else -> holder.binding.ivNotification.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: GetNotifResponseItem)
        fun onClickAcc(item: GetNotifResponseItem)
    }
}