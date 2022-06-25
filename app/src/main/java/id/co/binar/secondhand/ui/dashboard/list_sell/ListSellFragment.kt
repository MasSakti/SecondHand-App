package id.co.binar.secondhand.ui.dashboard.list_sell

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.FragmentListSellBinding
import id.co.binar.secondhand.ui.profile.PASSING_FROM_ACCOUNT_TO_PROFILE
import id.co.binar.secondhand.ui.profile.ProfileActivity
import id.co.binar.secondhand.util.Resource
import id.co.binar.secondhand.util.castFromLocalToRemote
import id.co.binar.secondhand.util.onSnackError

@AndroidEntryPoint
class ListSellFragment : Fragment() {

    private var _binding: FragmentListSellBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ListSellViewModel>()
    private val adapterCategory = ListSellCategoryAdapter()
    private val adapterProduct = ListSellProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListSellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getProduct()
        }

        binding.button3.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.putExtra(PASSING_FROM_ACCOUNT_TO_PROFILE, true)
            startActivity(intent)
        }

        binding.rvCategory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = true
            adapter = adapterCategory
        }

        adapterCategory.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            onClickAdapter { i, getCategoryResponseItem -> }
        }

        binding.rvList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            itemAnimator = DefaultItemAnimator()
            isNestedScrollingEnabled = false
            adapter = adapterProduct
        }

        adapterProduct.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            onClickAdapter { i, getCategoryResponseItem -> }
        }
    }

    private fun bindObserver() {
        viewModel.getProduct()

        viewModel.getProduct.observe(viewLifecycleOwner) {
            adapterProduct.asyncDiffer.submitList(it.data.castFromLocalToRemote())
            binding.rvList.adapter = adapterProduct
            when (it) {
                is Resource.Success -> binding.swipeRefresh.isRefreshing = false
                is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                is Resource.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    requireContext().onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }

        viewModel.getAccount().observe(viewLifecycleOwner) {
            binding.textView3.text = it.data?.fullName
            binding.textView4.text = it.data?.city
            binding.imageView.load(it.data?.imageUrl.toString()) {
                placeholder(R.drawable.ic_profile_image)
                error(R.drawable.ic_profile_image)
                transformations(RoundedCornersTransformation(14F))
                size(ViewSizeResolver(binding.imageView))
            }
            when (it) {
                is Resource.Success -> {}
                is Resource.Loading -> {}
                is Resource.Error -> {
                    requireContext().onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}