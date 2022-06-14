package id.co.binar.secondhand.ui.dashboard.list_sell

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.EntryPoint
import id.co.binar.secondhand.R

@EntryPoint
class ListSellFragment : Fragment() {

    companion object {
        fun newInstance() = ListSellFragment()
    }

    private lateinit var viewModel: ListSellViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_sell, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListSellViewModel::class.java)
        // TODO: Use the ViewModel
    }

}