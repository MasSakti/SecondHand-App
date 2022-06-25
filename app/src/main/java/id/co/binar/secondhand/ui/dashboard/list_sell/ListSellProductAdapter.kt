package id.co.binar.secondhand.ui.dashboard.list_sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ListItemProductHomeBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.model.seller.product.CategoriesItem
import id.co.binar.secondhand.model.seller.product.GetProductResponseItem
import id.co.binar.secondhand.util.convertRupiah

class ListSellProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val asyncDiffer = AsyncListDiffer(this, diffUtilCallback)
    private var _onClickAdapter: ((Int, GetProductResponseItem) -> Unit)? = null

    fun onClickAdapter(listener: (Int, GetProductResponseItem) -> Unit) {
        _onClickAdapter = listener
    }

    private fun List<CategoriesItem>.toNameOnly(): String {
        val str = mutableListOf<String>()
        this.forEach {
            str.add(it.name.toString())
        }
        return str.joinToString()
    }

    inner class ViewHolder(val binding: ListItemProductHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                _onClickAdapter?.invoke(bindingAdapterPosition, asyncDiffer.currentList[bindingAdapterPosition])
            }
        }

        fun bind(item: GetProductResponseItem) {
            binding.ivImageProduct.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.color.purple_100)
                error(R.color.purple_100)
                transformations(RoundedCornersTransformation(6F))
                size(ViewSizeResolver(binding.ivImageProduct))
            }
            binding.tvNamaProduct.text = item.name
            binding.tvHargaProduct.text = item.basePrice?.convertRupiah()
            binding.tvJenisProduct.text = item.categories?.toNameOnly()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemProductHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.bind(asyncDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return asyncDiffer.currentList.size
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<GetProductResponseItem>() {
    override fun areItemsTheSame(oldItem: GetProductResponseItem, newItem: GetProductResponseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GetProductResponseItem, newItem: GetProductResponseItem): Boolean {
        return oldItem == newItem
    }
}