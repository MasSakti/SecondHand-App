package binar.and3.kelompok1.secondhand.ui.menu.notifikasi

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import binar.and3.kelompok1.secondhand.common.ChangeCurrency
import binar.and3.kelompok1.secondhand.data.api.getNotification.GetNotifResponseItem
import binar.and3.kelompok1.secondhand.databinding.ListItemNotifikasiBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

        holder.binding.tvBarang.text = item.product_name
        Glide.with(holder.binding.root.context)
            .load(item.image_url)
            .transform(RoundedCorners(20))
            .into(holder.binding.ivImg)
        holder.binding.tvHarga.text = ChangeCurrency.gantiRupiah(item.bid_price.toString())

        val status = item.status
        for (item in 1..jumlahdata) {
            if (status == "bid") {
                holder.binding.tvJenisnotif.text = "Penawaran Produk"
            } else if (status == "accepted") {
                holder.binding.tvJenisnotif.text = "Penawaran Diterima"
            } else if (status == "declined") {
                holder.binding.tvJenisnotif.text = "Penawaran Ditolak"
            } else {
                holder.binding.tvJenisnotif.text = status
            }
        }

        /*val inputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm, dd MMM yyy", Locale.ENGLISH)
        val date: LocalDateTime = LocalDateTime.parse(item.transaction_date, inputFormatter)
        val formattedDate: String = outputFormatter.format(date)
        holder.binding.tvTanggal.text = formattedDate

        val data = item.read
        for (item in 1..jumlahdata) {
            if (data) {
                holder.binding.divNotif.visibility = View.GONE
            } else {
                holder.binding.divNotif.visibility = View.VISIBLE
            }
        }*/

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