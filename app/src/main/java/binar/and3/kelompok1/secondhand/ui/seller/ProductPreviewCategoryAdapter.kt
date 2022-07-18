package binar.and3.kelompok1.secondhand.ui.seller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductByIdResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemCategoriesBinding

class ProductPreviewCategoryAdapter(
    private var productPreviewCategory: List<GetProductByIdResponse.Categories>
): RecyclerView.Adapter<ProductPreviewCategoryAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataChanged")
    fun updateProductCategory(list: List<GetProductByIdResponse.Categories>) {
        this.productPreviewCategory = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryProduct = productPreviewCategory[position]
        holder.binding.tvCategory.text = categoryProduct.name
    }

    override fun getItemCount(): Int {
        return productPreviewCategory.size
    }
}