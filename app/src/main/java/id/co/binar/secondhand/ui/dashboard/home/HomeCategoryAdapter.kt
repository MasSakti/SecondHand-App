package id.co.binar.secondhand.ui.dashboard.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ListItemCategoryHomeBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem

class HomeCategoryAdapter : ListAdapter<GetCategoryResponseItem, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var setPosition: Int? = 0
    private var _onClickAdapter: ((Int, GetCategoryResponseItem) -> Unit)? = null

    fun onClickAdapter(listener: (Int, GetCategoryResponseItem) -> Unit) {
        _onClickAdapter = listener
    }

    inner class ViewHolder(val binding: ListItemCategoryHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                setPosition = bindingAdapterPosition
                notifyDataSetChanged()
            }
        }

        fun bind(item: GetCategoryResponseItem) {
            binding.txtCategory.text = item.name
            if (getItem(bindingAdapterPosition).check == true) {
                getItem(bindingAdapterPosition).check = true
                _onClickAdapter?.invoke(bindingAdapterPosition, getItem(bindingAdapterPosition))
                binding.root.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.purple_500))
                binding.imgView.setImageResource(R.drawable.ic_round_search_white)
                binding.txtCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            } else {
                getItem(bindingAdapterPosition).check = false
                _onClickAdapter?.invoke(bindingAdapterPosition, getItem(bindingAdapterPosition))
                binding.root.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.purple_100))
                binding.imgView.setImageResource(R.drawable.ic_round_search_24)
                binding.txtCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemCategoryHomeBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.bind(getItem(position))
        if (setPosition == position) {
            getItem(position).check = true
            _onClickAdapter?.invoke(position, getItem(position))
            holder.binding.root.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_500))
            holder.binding.imgView.setImageResource(R.drawable.ic_round_search_white)
            holder.binding.txtCategory.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        } else {
            getItem(position).check = false
            _onClickAdapter?.invoke(position, getItem(position))
            holder.binding.root.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.purple_100))
            holder.binding.imgView.setImageResource(R.drawable.ic_round_search_24)
            holder.binding.txtCategory.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<GetCategoryResponseItem>() {
    override fun areItemsTheSame(oldItem: GetCategoryResponseItem, newItem: GetCategoryResponseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GetCategoryResponseItem, newItem: GetCategoryResponseItem): Boolean {
        return oldItem == newItem
    }
}