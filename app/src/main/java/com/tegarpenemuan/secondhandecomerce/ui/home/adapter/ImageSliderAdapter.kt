package com.tegarpenemuan.secondhandecomerce.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tegarpenemuan.secondhandecomerce.data.api.banner.BannerResponseItem
import com.tegarpenemuan.secondhandecomerce.databinding.ItemSlideBinding

/**
 * com.tegarpenemuan.imageslider
 *
 * Created by Tegar Penemuan on 12/07/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

class ImageSliderAdapter(private val items: List<BannerResponseItem>):RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: ItemSlideBinding): RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        fun bind(data:BannerResponseItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(data.image_url)
                    .into(ivSlider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemSlideBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}