package id.co.binar.secondhand.ui.dashboard.list_sell.child

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.binar.secondhand.R

class ListSellByDiminatiFragment : Fragment() {

    companion object {
        fun newInstance() = ListSellByDiminatiFragment()
    }

    private lateinit var viewModel: ListSellByDiminatiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_sell_by_diminati, container, false)
    }
}