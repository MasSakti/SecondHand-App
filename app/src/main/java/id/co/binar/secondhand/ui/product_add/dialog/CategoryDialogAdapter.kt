package id.co.binar.secondhand.ui.product_add.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ListItemCategoryProductAddBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem

class CategoryDialogAdapter : ListAdapter<GetCategoryResponseItem, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var _onClickAdapter: ((Int, GetCategoryResponseItem) -> Unit)? = null

    inner class ViewHolder(private val binding: ListItemCategoryProductAddBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (getItem(bindingAdapterPosition).check == true) {
                    getItem(bindingAdapterPosition).check = false
                    _onClickAdapter?.invoke(bindingAdapterPosition, getItem(bindingAdapterPosition))
                    binding.imgCheck.setImageResource(R.drawable.ic_round_check_circle_outline_24)
                } else {
                    getItem(bindingAdapterPosition).check = true
                    _onClickAdapter?.invoke(bindingAdapterPosition, getItem(bindingAdapterPosition))
                    binding.imgCheck.setImageResource(R.drawable.ic_round_check_circle_24)
                }
            }
        }

        fun bind(item: GetCategoryResponseItem) {
            binding.txtName.text = item.name
            if (getItem(bindingAdapterPosition).check == true) {
                binding.imgCheck.setImageResource(R.drawable.ic_round_check_circle_24)
            } else {
                binding.imgCheck.setImageResource(R.drawable.ic_round_check_circle_outline_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ListItemCategoryProductAddBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.bind(getItem(position))
    }

    fun onClickAdapter(listener: (Int, GetCategoryResponseItem) -> Unit) {
        _onClickAdapter = listener
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<GetCategoryResponseItem>() {
    override fun areItemsTheSame(oldItem: GetCategoryResponseItem, newItem: GetCategoryResponseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GetCategoryResponseItem, newItem: GetCategoryResponseItem): Boolean {
        return oldItem == newItem
    }
}