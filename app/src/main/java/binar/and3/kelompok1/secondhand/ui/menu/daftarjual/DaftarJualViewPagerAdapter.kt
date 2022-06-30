package binar.and3.kelompok1.secondhand.ui.menu.daftarjual

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.ItemDiminatiFragment
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.ItemProdukFragment
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.ItemTerjualFragment

class DaftarJualViewPagerAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> fragment = ItemProdukFragment()
            1 -> fragment = ItemDiminatiFragment()
            2 -> fragment = ItemTerjualFragment()
        }
        return fragment
    }
}