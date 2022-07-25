package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerOrdersResponse
import binar.and3.kelompok1.secondhand.databinding.FragmentItemDiminatiBinding
import binar.and3.kelompok1.secondhand.ui.seller.infopenawar.InfoPenawarActivity
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.adapter.ItemDiminatiAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDiminatiFragment : Fragment() {

    private var _binding: FragmentItemDiminatiBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualItemViewModel by viewModels()
    lateinit var itemDiminatiAdapter: ItemDiminatiAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentItemDiminatiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        itemDiminatiAdapter =
            ItemDiminatiAdapter(listener = object : ItemDiminatiAdapter.EventListener {
                override fun onClick(item: GetSellerOrdersResponse) {
                    val intent = Intent(requireContext(), InfoPenawarActivity::class.java)
                    val bundle = Bundle()
                    item.id?.let { bundle.putInt("id", it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

            }, emptyList())

        binding.rvDiminati.adapter = itemDiminatiAdapter

        viewModel.getSellerOder()
        bindViewModel()

        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSellerOder()
    }

    private fun bindViewModel() {
        viewModel.shouldShowSellerOrder.observe(requireActivity()) { list ->
            itemDiminatiAdapter.updateProductOrder(
                list.filter {
                    it.status?.contains("pending")!!
                })
        }
    }

}