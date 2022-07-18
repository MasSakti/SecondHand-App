package binar.and3.kelompok1.secondhand.ui.menu.home.adapter.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemProductCardBinding
import com.bumptech.glide.Glide

class HomeProductAdapter(
    private val listener: EventListener,
    private var buyerProduct: List<BuyerProductResponse>

) : RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<BuyerProductResponse>() {
        override fun areItemsTheSame(
            oldItem: BuyerProductResponse,
            newItem: BuyerProductResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BuyerProductResponse,
            newItem: BuyerProductResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<BuyerProductResponse>) = differ.submitList(value)

    interface EventListener {
        fun onClick(item: BuyerProductResponse)
    }

    inner class ViewHolder(private val binding: ListItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        var listCategory = ""
        fun bind(data: BuyerProductResponse) {
            Glide.with(binding.root)
                .load(data.imageUrl)
                .into(binding.ivItem)
            binding.tvTitle.text = data.name
            if (data.categories.isNotEmpty()) {
                for (data in data.categories) {
                    listCategory += ", ${data.name}"
                }
                binding.tvCategory.text = listCategory.drop(2)
            }
            binding.tvHarga.text = data.basePrice?.let { currency(it) }
            binding.root.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemProductCardBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}