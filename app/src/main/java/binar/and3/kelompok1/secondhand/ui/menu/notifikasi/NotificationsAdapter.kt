package binar.and3.kelompok1.secondhand.ui.menu.notifikasi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import binar.and3.kelompok1.secondhand.common.convertDate
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.data.api.getNotification.GetNotifResponseItem
import binar.and3.kelompok1.secondhand.databinding.ListItemNotifikasiBinding

class NotificationsAdapter(
    private val listener: EventListener,
    private var list: List<GetNotifResponseItem>
) : RecyclerView.Adapter<NotificationsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemNotifikasiBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GetNotifResponseItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemNotifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val jumlahdata = list.count()

        holder.binding.tvBarang.text = item.productName
        Glide.with(holder.binding.root.context)
            .load(item.imageUrl)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivImg)

        val status = item.status

        holder.binding.apply {
            when (status) {
                "create" -> {
                    tvJenisnotif.text = "Info"
                    if (item.product != null) {
                        tvBarang.text = "Produk berhasil dibuat"
                        if (item.receiverId == item.product!!.userId) {
                            tvHarga.text = "Produk ${item.productName} behasil dibuat dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt())
                            }}"
                        } else {
                            tvHarga.text = "Produk ${item.productName} berhasil dibuat dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt())
                            }}"
                        }
                    } else {
                        tvBarang.text = "Produk dihapus oleh admin"
                        tvHarga.text = "Mohon maaf, produkmu dihapus oleh admin. Silakan hubungi Admin."
                    }
                }
                "bid" -> {
                    tvJenisnotif.text = "Tawar"
                    if (item.product != null) {
                        if (item.receiverId == item.product!!.userId) {
                            tvBarang.text = "Produkmu ditawar!"
                            tvHarga.text = "Produk ${item.productName} dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt())
                            }} ditawar sebesar ${item.bidPrice?.let { currency(it.toInt()) }}"
                        } else {
                            tvBarang.text = "Tawaran belum diterima penjual"
                            tvHarga.text = "Tawaran produk ${item.productName} dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt())
                            }} dengan tawaran ${item.bidPrice?.let { currency(it.toInt()) }} belum diterima oleh penjual, Mohon menunggu."
                        }
                    } else {
                        tvBarang.text = "Produk dihapus oleh admin"
                        tvHarga.text = "Mohon maaf, produk dihapus oleh admin. Silakan hubungi Admin."
                    }
                }
                "declined" -> {
                    tvJenisnotif.text = "Ditolak"
                    if (item.product !=null) {
                        if (item.receiverId == item.product!!.userId) {
                            tvBarang.text = "Kamu menolak tawaran ini"
                            tvHarga.text = "Kamu menolak tawaran produk ${item.productName} dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt())
                            }} yang ditawar sebesar ${item.bidPrice?.let { currency(it.toInt()) }}"
                        } else {
                            tvBarang.text = "Tawaranmu ditolak!"
                            tvHarga.text = "Kamu kurang beruntung. Tawaran pada produk ${item.productName} dengan harga ${item.basePrice?.let { 
                                currency(
                                    it.toInt()
                                )
                            }} yang kamu tawar sebesar ${item.bidPrice?.let { currency(it.toInt()) }} ditolak penjual"
                        }
                    } else {
                        tvBarang.text = "Produk dihapus oleh admin"
                        tvHarga.text = "Mohon maaf, produk dihapus oleh admin. Silakan hubungi Admin."
                    }
                }
                "accepted" -> {
                    tvJenisnotif.text = "Diterima"
                    if (item.product != null) {
                        if (item.receiverId == item.product!!.userId) {
                            tvBarang.text = "Kamu menerima tawaran ini"
                            tvHarga.text = "Kamu menerima tawaran produk ${item.productName} dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt()
                                )
                            }} yang ditawar sebesar ${item.bidPrice?.let { currency(it.toInt()) }}"
                        } else {
                            tvBarang.text = "Tawaranmu diterima oleh penjual!"
                            tvHarga.text = "Selamat! Tawaran pada ${item.productName} dengan harga ${item.basePrice?.let {
                                currency(
                                    it.toInt()
                                )
                            }} berhasil kamu tawar ${item.bidPrice?.let { currency(it.toInt()) }}. Mohon tunggu response penjual."
                        }
                    } else {
                        tvBarang.text = "Produk dihapus oleh admin"
                        tvHarga.text = "Mohon maaf, produk dihapus oleh admin. Silakan hubungi Admin."
                    }
                }
                else -> {
                    tvBarang.text = "Produk dihapus oleh admin"
                    tvHarga.text = "Mohon maaf, produk dihapus oleh admin. Silakan hubungi Admin."
                }
            }
        }


        holder.binding.tvTanggal.text = item.transactionDate?.let { convertDate(it) }

        val data = item.read
        for (item in 1..jumlahdata) {
            if (data == true) {
                holder.binding.divNotif.visibility = View.GONE
            } else {
                holder.binding.divNotif.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: GetNotifResponseItem)
    }
}