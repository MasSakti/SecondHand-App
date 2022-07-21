package com.example.projectgroup2.ui.main.notif.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectgroup2.data.api.main.notification.NotifResponse
import com.example.projectgroup2.databinding.ListItemNotificationBinding
import com.example.projectgroup2.utils.convertDate
import com.example.projectgroup2.utils.currency
import com.example.projectgroup2.utils.striketroughtText

class NotifAdapter(
    private val onItemClick : OnClickListener
) :
    RecyclerView.Adapter<NotifAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<NotifResponse>(){
        override fun areItemsTheSame(
            oldItem: NotifResponse,
            newItem: NotifResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotifResponse,
            newItem: NotifResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<NotifResponse>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemNotificationBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ListItemNotificationBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: NotifResponse){
            binding.apply {
                when (data.status) {
                    "create" -> {
                        tvNotificationInfo.text = "Berhasil membuat product"
                        if (data.product != null){
                            if (data.receiverId == data.product.userId){
                                tvSpesifikAktivitasInfo.text = "Berhasil membuat product"
                            } else {
                                tvSpesifikAktivitasInfo.text = "Berhasil membuat product"
                            }
                        } else {
                            tvSpesifikAktivitasInfo.text = "Produk Sudah di hapus"
                        }
                    }
                    "bid" -> {
                        if (data.product != null){
                            if(data.receiverId == data.product.userId){
                                tvSpesifikAktivitasInfo.text = "Ada yang tawar produkmu!"
                            } else {
                                tvSpesifikAktivitasInfo.text = "Tawaranmu belum diterima oleh penjual, sabar ya!"
                            }
                        } else {
                            tvSpesifikAktivitasInfo.text = "Produk Sudah di hapus"
                        }
                    }
                    "declined" -> {
                        tvNotificationInfo.text = "Produk Ditolak"
                        if (data.product != null){
                            if (data.receiverId == data.product.userId){
                                tvSpesifikAktivitasInfo.text = "Anda menolak Tawaran ini"
                            } else {
                                tvSpesifikAktivitasInfo.text = "Tawaran Anda ditolak oleh Penjual"
                            }
                        } else {
                            tvSpesifikAktivitasInfo.text = "Produk Sudah di hapus"
                        }
                    }
                    "accepted" -> {
                        tvNotificationInfo.text = "Produk Diterima"
                        if (data.product != null){
                            if (data.receiverId == data.product.userId){
                                tvSpesifikAktivitasInfo.text = "Anda menerima Tawaran ini"
                            } else {
                                tvSpesifikAktivitasInfo.text = "Tawaran Anda diterima oleh Penjual"
                            }
                        } else {
                            tvSpesifikAktivitasInfo.text = "Produk Sudah di hapus"
                        }
                    }
                    else -> {
                        tvSpesifikAktivitasInfo.text = " "
                    }
                }
                tvAktivitasInfo.text =
                    when (data.status) {
                        "declined" -> "Ditolak " + currency(data.bidPrice)
                        "accepted" -> "Diterima " + currency(data.bidPrice)
                        else -> "Ditawar " + currency(data.bidPrice)
                    }
//                if(data.status == "declined"){
//                    tvAktivitasInfo.text = "Ditolak " + currency(data.bidPrice)
//                }else if(data.status == "accepted"){
//                    tvAktivitasInfo.text = "Diterima " + currency(data.bidPrice)
//                }else if(data.status == "create"){
//                    tvAktivitasInfo.visibility = View.GONE
//                }else if(data.status == "bid"){
//                    tvAktivitasInfo.text = "Ditawar " + currency(data.bidPrice)
//                }
                tvProdukInfo.text = data.productName
                tvHargaInfo.apply {
                    text = if(data.basePrice.isNotEmpty()) striketroughtText(this, currency(data.basePrice.toInt())) else "-"
                }
                tvTanggalNotification.text = convertDate(data.updatedAt)
                if (!data.read){
                    Glide.with(binding.root)
                        .load(data.imageUrl)
                        .centerCrop()
                        .into(ivGambarProduk)
                }
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClickItem(data: NotifResponse)
    }
}