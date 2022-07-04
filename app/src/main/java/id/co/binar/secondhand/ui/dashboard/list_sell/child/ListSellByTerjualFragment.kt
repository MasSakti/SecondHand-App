package id.co.binar.secondhand.ui.dashboard.list_sell.child

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.binar.secondhand.R

class ListSellByTerjualFragment : Fragment() {

    companion object {
        fun newInstance() = ListSellByTerjualFragment()
    }

    private lateinit var viewModel: ListSellByTerjualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_sell_by_terjual, container, false)
    }
}