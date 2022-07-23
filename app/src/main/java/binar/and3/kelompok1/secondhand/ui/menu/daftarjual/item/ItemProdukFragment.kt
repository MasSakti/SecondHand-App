package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductResponse
import binar.and3.kelompok1.secondhand.databinding.FragmentItemProdukBinding
import binar.and3.kelompok1.secondhand.ui.jualform.JualFormActivity
import binar.and3.kelompok1.secondhand.ui.adapterproductcard.ItemProdukAdapter
import binar.and3.kelompok1.secondhand.ui.seller.ProductPreviewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemProdukFragment : Fragment() {

    private var _binding: FragmentItemProdukBinding? = null
    private val binding get() = _binding!!
    lateinit var itemProdukAdapter: ItemProdukAdapter
    private val viewModel: DaftarJualItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemProdukBinding.inflate(inflater, container, false)
        val root: View = binding.root

        itemProdukAdapter =
            ItemProdukAdapter(listener = object : ItemProdukAdapter.EventListener {
                override fun onClick(item: GetProductResponse) {
                    val intent = Intent(activity, ProductPreviewActivity::class.java)
                    val bundle = Bundle()
                    item.id?.let { bundle.putInt("id", it) }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }, emptyList())

        binding.rvDaftarJual.adapter = itemProdukAdapter

        viewModel.onViewLoaded()
        bindViewModel()

        // Inflate the layout for this fragment
        return root
    }

    private fun bindViewModel() {
        viewModel.shouldShowMyProduct.observe(requireActivity()) {
            itemProdukAdapter.updateSellerProduct(it)
        }
    }

}