package binar.and3.kelompok1.secondhand.ui.menu.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategory
import binar.and3.kelompok1.secondhand.databinding.FragmentProductByCategoryBinding
import binar.and3.kelompok1.secondhand.model.ProductDetailModel

class HomeTabLayoutAdapter(
    fragment: Fragment,
    private val productCategory: List<GetSellerCategory>
): FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        val category = productCategory[position]
        fragment = ProductByCategoryFragment()
        return fragment
    }

    override fun getItemCount(): Int {
        return productCategory.size
    }
}