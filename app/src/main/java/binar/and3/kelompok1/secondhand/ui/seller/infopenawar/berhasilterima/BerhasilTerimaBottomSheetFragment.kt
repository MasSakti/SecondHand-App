package binar.and3.kelompok1.secondhand.ui.seller.infopenawar.berhasilterima

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.common.currency
import binar.and3.kelompok1.secondhand.databinding.FragmentBerhasilTerimaBottomSheetBinding
import binar.and3.kelompok1.secondhand.ui.seller.infopenawar.InfoPenawarViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BerhasilTerimaBottomSheetFragment(private val productId: Int) : BottomSheetDialogFragment() {

    private var _binding: FragmentBerhasilTerimaBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InfoPenawarViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBerhasilTerimaBottomSheetBinding.inflate(inflater, container, false)

        viewModel.onViewLoaded(id = productId)
        bindViewModel()
        bindView()

        return binding.root
    }

    private fun bindView() {
        binding.btnHubungi.setOnClickListener {
            viewModel.shouldShowOrder.observe(viewLifecycleOwner) {
                val link = "https://api.whatsapp.com/send?phone=%s&text=%s"
                val phoneNumber = "+62" + it.user?.phoneNumber
                val message =
                    "Halo ${it.user?.fullName}, kami menyetujui tawaranmu pada produk *${it.productName}* dengan harga yang kamu tawar sebesar *${
                        it.price?.let { it1 ->
                            currency(
                                it1
                            )
                        }
                    }*. Mohon balasannya untuk diskusi selanjutnya. 😁"

                /*val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, message)
                    type = "text/plain"
                }
                sendIntent.setPackage("com.whatsapp")
                val shareIntent = Intent.createChooser(sendIntent, null)*/
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                    String.format(link, phoneNumber, message)
                )))
            }
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowOrder.observe(viewLifecycleOwner) {
            // User
            Glide.with(requireContext())
                .load(it.user?.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                .into(binding.ivUserImage)
            binding.tvUserName.text = it.user?.fullName
            binding.tvUserCity.text = it.user?.city

            // Product
            Glide.with(requireContext())
                .load(it.imageProduct)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                .into(binding.ivProductImage)
            binding.tvProductName.text = it.productName
            binding.tvProductBasePrice.text = "Rp ${it.basePrice}"
            binding.tvProductBasePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvProductBid.text = "Ditawar Rp ${it.price}"

            val phoneNumber = it.user?.phoneNumber
        }
    }

}