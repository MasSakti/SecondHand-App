package binar.and3.kelompok1.secondhand.ui.menu.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategoryResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemCategoryButtonsBinding

class HomeCategoryButtonAdapter(
    private val listener: EventListener,
    private var sellerCategory: List<GetSellerCategoryResponse>
) : RecyclerView.Adapter<HomeCategoryButtonAdapter.ViewHolder>() {

    var rowIndex = -1
    private val diffCallBack = object : DiffUtil.ItemCallback<GetSellerCategoryResponse>() {
        override fun areItemsTheSame(
            oldItem: GetSellerCategoryResponse,
            newItem: GetSellerCategoryResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetSellerCategoryResponse,
            newItem: GetSellerCategoryResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<GetSellerCategoryResponse>?) = differ.submitList(value)

    inner class ViewHolder(val binding: ListItemCategoryButtonsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bind (data: GetSellerCategoryResponse, position: Int) {
            binding.tvCategory.text = data.name
            binding.root.setOnClickListener {
                listener.onClick(data)
                rowIndex = position
                notifyDataSetChanged()
            }
            if (rowIndex == position) {
                binding.llCategory.setBackgroundColor(Color.parseColor("#7126B5"))
                binding.tvCategory.setTextColor(Color.parseColor("#FFFFFF"))
                binding.ivSearch.setColorFilter(Color.parseColor("#FFFFFF"))
            } else {
                binding.llCategory.setBackgroundColor(Color.parseColor("#E2D4F0"))
                binding.tvCategory.setTextColor(Color.parseColor("#3C3C3C"))
                binding.ivSearch.setColorFilter(Color.parseColor("#3C3C3C"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemCategoryButtonsBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data, position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface EventListener {
        fun onClick(item: GetSellerCategoryResponse)
    }
}