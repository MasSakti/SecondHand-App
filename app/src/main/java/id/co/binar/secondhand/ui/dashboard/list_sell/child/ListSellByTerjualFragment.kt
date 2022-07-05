package id.co.binar.secondhand.ui.dashboard.list_sell.child

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.FragmentListSellByTerjualBinding

@AndroidEntryPoint
class ListSellByTerjualFragment : Fragment() {

    private var _binding : FragmentListSellByTerjualBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListSellByTerjualViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListSellByTerjualBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() { }

    private fun bindObserver() { }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}