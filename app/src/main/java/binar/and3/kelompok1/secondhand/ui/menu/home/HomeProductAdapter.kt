package binar.and3.kelompok1.secondhand.ui.menu.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProduct
import binar.and3.kelompok1.secondhand.databinding.ListItemHomeBinding
import com.bumptech.glide.Glide

class HomeProductAdapter(
    private val listener: EventListener,
    private var buyerProduct: List<BuyerProduct.BuyerProductResponse>

) : RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataChanged")
    fun updateBuyerProduct(list: List<BuyerProduct.BuyerProductResponse>) {
        this.buyerProduct = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val buyerProduct = buyerProduct[position]
        Glide.with(holder.binding.root.context)
            .load(buyerProduct.imageUrl)
            .into(holder.binding.ivItem)

        holder.binding.tvTitle.text = buyerProduct.name

        holder.itemView.setOnClickListener {
            listener.onClick(item = buyerProduct)
        }
    }

    override fun getItemCount(): Int {
        return  buyerProduct.size
    }

    interface EventListener{
        fun onClick(item: BuyerProduct.BuyerProductResponse)
    }

}