package binar.and3.kelompok1.secondhand.ui.menu.jual

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import binar.and3.kelompok1.secondhand.databinding.FragmentJualBinding
import binar.and3.kelompok1.secondhand.ui.jualform.JualFormViewModel
import binar.and3.kelompok1.secondhand.ui.menu.daftarjual.DaftarJualFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JualFragment : Fragment() {

    private var _binding: FragmentJualBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JualFormViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.WHITE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJualBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bindView()
        bindViewModel()
        viewModel.getSellerCategory()

        return root
    }

    private fun bindView() {
        binding.etNamaProduk.doAfterTextChanged {
            viewModel.onChangeName(it.toString())
        }
        binding.etDeskripsi.doAfterTextChanged {
            viewModel.onChangeDescription(it.toString())
        }
        binding.etHargaProduk.doAfterTextChanged {
            viewModel.onChangeBasePrice(it.hashCode())
        }
        binding.etKategori.doAfterTextChanged {
            viewModel.onChangeBasePrice(it.hashCode())
        }
        binding.ivFotoProduk.setOnClickListener {

        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowLoading.observe(viewLifecycleOwner) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }
        viewModel.shouldShowError.observe(viewLifecycleOwner) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }
        viewModel.shouldOpenDaftarJual.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(requireContext(), DaftarJualFragment::class.java)
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