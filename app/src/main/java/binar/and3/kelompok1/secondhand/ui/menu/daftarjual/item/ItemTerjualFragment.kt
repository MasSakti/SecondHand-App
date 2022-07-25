package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerOrdersResponse
import binar.and3.kelompok1.secondhand.databinding.FragmentItemTerjualBinding
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.adapter.ItemDiminatiAdapter
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item.adapter.ItemTerjualAdapter
import binar.and3.kelompok1.secondhand.ui.seller.infopenawar.InfoPenawarActivity
import binar.and3.kelompok1.secondhand.ui.seller.productpreview.ProductPreviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemTerjualFragment : Fragment() {

    private var _binding: FragmentItemTerjualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualItemViewModel by viewModels()
    lateinit var itemOrderAdapter: ItemTerjualAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemTerjualBinding.inflate(inflater, container, false)

        itemOrderAdapter =
            ItemTerjualAdapter(listener = object : ItemTerjualAdapter.EventListener {
                override fun onClick(item: GetSellerOrdersResponse) {
                    val intent = Intent(requireContext(), ProductPreviewActivity::class.java)
                    val bundle = Bundle()
                    item.productId?.let { bundle.putInt("id", it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }, emptyList())

        binding.rvTerjual.adapter = itemOrderAdapter
        viewModel.getSellerOder()
        bindViewModel()

        return binding.root
    }

    private fun bindViewModel() {
        viewModel.shouldShowSellerOrder.observe(requireActivity()) { list ->
            itemOrderAdapter.updateProductOrder(
                list.filter {
                    it.product?.status?.let { status -> "sold".contains(status) }!!
                })
        }
    }

}