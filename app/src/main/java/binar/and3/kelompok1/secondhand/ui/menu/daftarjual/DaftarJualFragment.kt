package binar.and3.kelompok1.secondhand.ui.menu.daftarjual

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.R
import binar.and3.kelompok1.secondhand.databinding.FragmentDaftarJualBinding
import binar.and3.kelompok1.secondhand.ui.lengkapiinfoakun.LengkapiInfoAkunActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarJualFragment : Fragment() {

    private var _binding: FragmentDaftarJualBinding? = null
    private val binding get() = _binding!!
    private lateinit var daftarJualViewPagerAdapter: DaftarJualViewPagerAdapter
    private val viewModel: DaftarJualViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.WHITE
    }

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
                    0 -> {
                        tab.text = "Produk"
                        tab.setIcon(R.drawable.ic_fi_box)
                    }
                    1 -> {
                        tab.text = "Diminati"
                        tab.setIcon(R.drawable.ic_fi_heart)
                    }
                    2 -> {
                        tab.text = "Terjual"
                        tab.setIcon(R.drawable.ic_fi_dollar_sign)
                    }
                }
            }.attach()
        }

        viewModel.getProfile()
        bindView()
        bindViewModel()

        return root
    }

    private fun bindView() {
        binding.btnEdit.setOnClickListener {
            val intent = Intent(context, LengkapiInfoAkunActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
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