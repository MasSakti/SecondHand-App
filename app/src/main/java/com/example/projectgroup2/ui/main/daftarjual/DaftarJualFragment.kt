package com.example.projectgroup2.ui.main.daftarjual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.projectgroup2.R
import com.example.projectgroup2.data.api.main.buyer.product.GetProductResponse
import com.example.projectgroup2.data.api.main.sellerorder.SellerOrderResponse
import com.example.projectgroup2.data.api.main.sellerproduct.SellerProductResponse
import com.example.projectgroup2.databinding.FragmentDaftarJualBinding
import com.example.projectgroup2.ui.main.daftarjual.adapter.SellerOrderAdapter
import com.example.projectgroup2.ui.main.daftarjual.adapter.SellerProductAdapter
import com.example.projectgroup2.ui.main.home.HomeFragment
import com.example.projectgroup2.ui.main.home.adapter.ProductAdapter
import com.example.projectgroup2.utils.listCategory
import com.example.projectgroup2.utils.listCategoryId
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {
    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DaftarJualViewModel by viewModels()
    private val bundle = Bundle()
    private val bundlePenawar = Bundle()
    private val bundleEdit = Bundle()
    private lateinit var sellerProductAdapter: SellerProductAdapter
    private lateinit var sellerOrderAdapter: SellerOrderAdapter

    companion object {
        const val ORDER_ID = "OrderId"
        const val ORDER_STATUS = "OrderStatus"
        const val USER_NAME = "UserName"
        const val USER_CITY = "UserCity"
        const val USER_PHONE_NUMBER = "UserPhoneNumber"
        const val USER_IMAGE = "UserImage"
        const val PRODUCT_IMAGE = "ProductImage"
        const val PRODUCT_NAME = "ProductName"
        const val PRODUCT_DESCRIPTION = "productDescription"
        const val PRODUCT_PRICE = "ProductPrice"
        const val PRODUCT_LOCATION = "ProductLocation"
        const val PRODUCT_BID = "ProductBid"
        const val PRODUCT_BID_DATE = "ProductBidDate"
        const val PRODUCT_ID = "productId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()

        bindViewModel()
        bindView()
        adapterProduct()
    }

    private fun bindViewModel(){
        viewModel.showUser.observe(viewLifecycleOwner){
            binding.tvNamaPenjual.text = it.fullName
            binding.tvKotaPenjual.text = it.address
            Glide.with(this@DaftarJualFragment)
                .load(it.imageUrl)
                .into(binding.ivGambarPenjual)
        }
    }

    private fun bindView(){
        binding.btnProduct.setOnClickListener {
            adapterProduct()
            binding.rvDiminatiDaftarJual.visibility = View.GONE
            binding.rvDitolakDaftarJual.visibility = View.GONE
            binding.rvDiterimaDaftarJual.visibility = View.GONE
        }

        binding.btnDiminati.setOnClickListener {
            adapterOrder()
            binding.rvProductDaftarJual.visibility = View.GONE
            binding.rvDitolakDaftarJual.visibility = View.GONE
            binding.rvDiterimaDaftarJual.visibility = View.GONE
        }

        binding.btnDitolak.setOnClickListener {
            adapterOrderDitolak()
            binding.rvProductDaftarJual.visibility = View.GONE
            binding.rvDiminatiDaftarJual.visibility = View.GONE
            binding.rvDiterimaDaftarJual.visibility = View.GONE
        }

        binding.btnDiterima.setOnClickListener {
            adapterOrderDiterima()
            binding.rvProductDaftarJual.visibility = View.GONE
            binding.rvDiminatiDaftarJual.visibility = View.GONE
            binding.rvDitolakDaftarJual.visibility = View.GONE
        }
    }

    private fun adapterProduct(){
        viewModel.getProductSeller()
        viewModel.showProductSeller.observe(viewLifecycleOwner){
            val sellerProductAdapter = SellerProductAdapter(object: SellerProductAdapter.OnClickListener {
                override fun onClickItem(data: SellerProductResponse) {
                    bundleEdit.apply {
                        putInt(PRODUCT_ID, data.id)
                        putString(PRODUCT_NAME, data.name)
                        putInt(PRODUCT_PRICE, data.basePrice)
                        listCategory.clear()
                        listCategoryId.clear()
                        for (kategori in data.categories){
                            listCategory.add(kategori.name)
                            listCategoryId.add(kategori.id)
                        }
                        putString(PRODUCT_DESCRIPTION,data.description)
                        putString(PRODUCT_IMAGE,data.imageUrl)
                        putString(PRODUCT_LOCATION,data.location)
                    }
                    findNavController().navigate(R.id.action_daftarJualFragment_to_editProductFragment, bundleEdit)
                }
            })
            sellerProductAdapter.submitData(it)
            binding.rvProductDaftarJual.adapter = sellerProductAdapter
            binding.rvProductDaftarJual.visibility = View.VISIBLE
        }
    }

    private fun adapterOrder(){
        val statusPending = "pending"
        viewModel.getOrderSeller(statusPending)
        viewModel.showOrderSeller.observe(viewLifecycleOwner){
            val sellerOrderAdapter = SellerOrderAdapter(object: SellerOrderAdapter.OnClickListener {
                override fun onClickItem(data: SellerOrderResponse) {
                    bundlePenawar.apply {
                        putString(USER_NAME, data.buyerInformation.fullName)
                        putString(USER_CITY, data.buyerInformation.address)
                        putString(USER_PHONE_NUMBER, data.buyerInformation.phoneNumber)
                        putInt(ORDER_ID, data.id)
                        putString(ORDER_STATUS, data.status)
                        putString(PRODUCT_NAME, data.product.name)
                        putString(PRODUCT_PRICE, data.product.basePrice.toString())
                        putString(PRODUCT_BID, data.price.toString())
                        putString(PRODUCT_IMAGE, data.product.imageUrl)
                        putString(PRODUCT_BID_DATE, data.createdAt)
                    }
                    findNavController().navigate(R.id.action_daftarJualFragment_to_infoPenawarFragment, bundlePenawar)
                }
            })
            sellerOrderAdapter.submitData(it)
            binding.rvDiminatiDaftarJual.adapter = sellerOrderAdapter
            binding.rvDiminatiDaftarJual.visibility = View.VISIBLE
        }
    }

    private fun adapterOrderDitolak(){
        val statusDeclined = "declined"
        viewModel.getOrderSeller(statusDeclined)
        viewModel.showOrderSeller.observe(viewLifecycleOwner){
            val sellerOrderAdapter = SellerOrderAdapter(object: SellerOrderAdapter.OnClickListener {
                override fun onClickItem(data: SellerOrderResponse) {

                }
            })
            sellerOrderAdapter.submitData(it)
            binding.rvDitolakDaftarJual.adapter = sellerOrderAdapter
            binding.rvDitolakDaftarJual.visibility = View.VISIBLE
        }
    }

    private fun adapterOrderDiterima(){
        val statusAccepted = "accepted"
        viewModel.getOrderSeller(statusAccepted)
        viewModel.showOrderSeller.observe(viewLifecycleOwner){
            val sellerOrderAdapter = SellerOrderAdapter(object: SellerOrderAdapter.OnClickListener {
                override fun onClickItem(data: SellerOrderResponse) {
                    bundlePenawar.apply {
                        putString(USER_NAME, data.buyerInformation.fullName)
                        putString(USER_CITY, data.buyerInformation.address)
                        putString(USER_PHONE_NUMBER, data.buyerInformation.phoneNumber)
                        putInt(ORDER_ID, data.id)
                        putString(ORDER_STATUS, data.status)
                        putString(PRODUCT_NAME, data.product.name)
                        putString(PRODUCT_PRICE, data.product.basePrice.toString())
                        putString(PRODUCT_BID, data.price.toString())
                        putString(PRODUCT_IMAGE, data.product.imageUrl)
                        putString(PRODUCT_BID_DATE, data.createdAt)
                    }
                    findNavController().navigate(R.id.action_daftarJualFragment_to_infoPenawarFragment, bundlePenawar)
                }
            })
            sellerOrderAdapter.submitData(it)
            binding.rvDiterimaDaftarJual.adapter = sellerOrderAdapter
            binding.rvDiterimaDaftarJual.visibility = View.VISIBLE
        }
    }
}