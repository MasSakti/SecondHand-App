package binar.and3.kelompok1.secondhand.ui.menu.jual.bottomsheets

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.and3.kelompok1.secondhand.common.listCategory
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategoryResponse
import binar.and3.kelompok1.secondhand.databinding.ListItemCheckCategoryBinding

class CategoryBottomSheetsAdapter(
    private val selected: (GetSellerCategoryResponse) -> Unit,
    private val unselected: (GetSellerCategoryResponse) -> Unit
) : RecyclerView.Adapter<CategoryBottomSheetsAdapter.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<GetSellerCategoryResponse>(){

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
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<GetSellerCategoryResponse>?) = differ.submitList(value)

    inner class ViewHolder(private val binding: ListItemCheckCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: GetSellerCategoryResponse) {
            binding.apply {
                cbCategory.text = data.name
                cbCategory.isChecked = listCategory.contains(data.name)
                cbCategory.setOnClickListener{
                    if (!listCategory.contains(data.name)) {
                        selected.invoke(data)
                    } else {
                        unselected.invoke(data)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListItemCheckCategoryBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}