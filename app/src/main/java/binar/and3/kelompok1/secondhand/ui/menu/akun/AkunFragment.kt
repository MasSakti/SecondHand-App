package binar.and3.kelompok1.secondhand.ui.menu.akun

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.databinding.FragmentAkunBinding
import binar.and3.kelompok1.secondhand.ui.lengkapiinfoakun.LengkapiInfoAkunActivity
import binar.and3.kelompok1.secondhand.ui.signin.LoginUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AkunViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.WHITE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)

        viewModel.getProfile()
        bindViewModel()
        bindView()

        return binding.root
    }

    private fun bindView() {

        binding.llSignOut.setOnClickListener {
            viewModel.signOut()
        }
        binding.llChangeAccount.setOnClickListener {
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
                .into(binding.ivImageAccount)
        }
        viewModel.shouldShowLogin.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(requireContext(), LoginUI::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}