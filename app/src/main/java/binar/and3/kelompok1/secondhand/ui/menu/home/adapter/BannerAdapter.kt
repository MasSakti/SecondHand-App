package binar.and3.kelompok1.secondhand.ui.menu.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerBannerResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemBannerBinding
import com.bumptech.glide.Glide

class BannerAdapter(
    private var banner: List<GetSellerBannerResponse>
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: ListItemBannerBinding
    ) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataChanged")
    fun updateBanner(list: List<GetSellerBannerResponse>) {
        this.banner = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val banner = banner[position]

        Glide.with(holder.binding.root.context)
            .load(banner.imageUrl)
            .into(holder.binding.ivBanner)
    }

    override fun getItemCount(): Int {
        return banner.size
    }
}