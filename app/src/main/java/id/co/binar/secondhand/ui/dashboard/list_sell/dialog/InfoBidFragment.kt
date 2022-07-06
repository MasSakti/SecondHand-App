package id.co.binar.secondhand.ui.dashboard.list_sell.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.BottomSheetInfoBidSellerBinding
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.onSnackError

const val TAG_INFO_BID_DIALOG = "INFO_BID_DIALOG"

@AndroidEntryPoint
class InfoBidFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetInfoBidSellerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<InfoBidViewModel>()

    companion object {
        @JvmStatic
        fun newInstance(sectionNumber: Int): InfoBidFragment {
            return InfoBidFragment().apply {
                arguments = Bundle().apply {
                    putInt(TAG_INFO_BID_DIALOG, sectionNumber)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.args(arguments?.getInt(TAG_INFO_BID_DIALOG) ?: -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInfoBidSellerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.getOrderById.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.apply {
                        textView3.text = it.data?.user?.fullName
                        textView4.text = it.data?.user?.city
                        imageView.load(it.data?.user?.imageUrl.toString()) {
                            placeholder(R.drawable.ic_profile_image)
                            error(R.drawable.ic_profile_image)
                            transformations(RoundedCornersTransformation(14F))
                            size(ViewSizeResolver(imageView))
                        }
                    }
                }
                is Resource.Loading -> { }
                is Resource.Error -> requireContext().onSnackError(binding.root, it.error?.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}