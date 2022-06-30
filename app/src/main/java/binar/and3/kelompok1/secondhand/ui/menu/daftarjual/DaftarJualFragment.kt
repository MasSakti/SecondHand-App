package binar.and3.kelompok1.secondhand.ui.menu.daftarjual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.databinding.FragmentDaftarJualBinding
import binar.and3.kelompok1.secondhand.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private lateinit var daftarJualViewPagerAdapter: DaftarJualViewPagerAdapter
    private val viewModel: DaftarJualViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaftarJualBinding.inflate(inflater, container, false)
        val root: View = binding.root

        daftarJualViewPagerAdapter = DaftarJualViewPagerAdapter(this)
        with(binding) {
            vp2DaftarJual.adapter = daftarJualViewPagerAdapter

            TabLayoutMediator(tabLayout, vp2DaftarJual) { tab, position ->
                when(position) {
                    0 -> tab.text = "Produk"
                    1 -> tab.text = "Diminati"
                    2 -> tab.text = "Terjual"
                }
            }.attach()
        }

        viewModel.getProfile()
        bindViewModel()

        return root
    }

    private fun bindViewModel() {
        viewModel.shouldShowProfile.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                .into(binding.ivAvatar)

            with(binding) {
                tvName.text = it.full_name
                tvCity.text = it.city
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}