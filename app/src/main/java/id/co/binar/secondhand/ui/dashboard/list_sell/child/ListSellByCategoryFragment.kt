package id.co.binar.secondhand.ui.dashboard.list_sell.child

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.databinding.FragmentListSellByCategoryBinding
import id.co.binar.secondhand.ui.dashboard.list_sell.ListSellViewModel

@AndroidEntryPoint
class ListSellByCategoryFragment : Fragment() {

    private var _binding: FragmentListSellByCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListSellViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListSellByCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}