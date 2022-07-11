package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductByIdResponse
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemCategoriesBinding

class ItemCategoryAdapter(
    private val productCategory: List<GetProductResponse.Categories>
) : RecyclerView.Adapter<ItemCategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryProduct = productCategory[position]
        holder.binding.tvCategory.text = categoryProduct.name
    }

    override fun getItemCount(): Int {
        return productCategory.size
    }
}