package binar.and3.kelompok1.secondhand.ui.adapterproductcard

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemCategoriesBinding
import binar.and3.kelompok1.secondhand.databinding.ListItemProductCardBinding
import com.bumptech.glide.Glide

class ItemProdukAdapter(
    private val listener: EventListener,
    private var sellerProduct: List<GetProductResponse>
) : RecyclerView.Adapter<ItemProdukAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ListItemProductCardBinding
    ) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataChanged")
    fun updateSellerProduct(list: List<GetProductResponse>) {
        this.sellerProduct = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sellerProduct = sellerProduct[position]
        val categoryAdapter = ItemCategoryAdapter(sellerProduct.categories)
        holder.binding.rvCategories.adapter = categoryAdapter

        Glide.with(holder.binding.root.context)
            .load(sellerProduct.imageUrl)
            .into(holder.binding.ivItem)
        holder.binding.tvTitle.text = sellerProduct.name
        holder.binding.tvHarga.text = "Rp " + sellerProduct.basePrice.toString()
        holder.itemView.setOnClickListener {
            listener.onClick(item = sellerProduct)
        }
        holder.binding.tvCategory.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return sellerProduct.size
    }

    interface EventListener {
        fun onClick(item: GetProductResponse)
    }

}