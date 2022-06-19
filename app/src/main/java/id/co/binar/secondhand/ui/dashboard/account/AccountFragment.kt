package id.co.binar.secondhand.ui.dashboard.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.FragmentAccountBinding
import id.co.binar.secondhand.ui.login.LoginActivity
import id.co.binar.secondhand.ui.login.PASSING_TO_SIGN_IN
import id.co.binar.secondhand.util.DataStoreManager
import id.co.binar.secondhand.util.TOKEN_ID
import id.co.binar.secondhand.util.dataStore
import id.co.binar.secondhand.util.getValue
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AccountViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.tvLogoutAccount.setOnClickListener {
            dialogLogout {
                viewModel.logout()
                it.dismiss()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
        binding.btnMasuk.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindObserver() {
        lifecycleScope.launch {
            requireContext().dataStore.getValue(TOKEN_ID, "").collectLatest {
                if (it.isNullOrEmpty()) {
                    binding.apply {
                        ivEditAccount.visibility = View.INVISIBLE
                        ivLogoutAccount.visibility = View.INVISIBLE
                        ivOptionAccount.visibility = View.INVISIBLE
                        tvEditAccount.visibility = View.INVISIBLE
                        tvLogoutAccount.visibility = View.INVISIBLE
                        tvOptionAccount.visibility = View.INVISIBLE
                        viewEdit.visibility = View.INVISIBLE
                        viewLogout.visibility = View.INVISIBLE
                        viewOption.visibility = View.INVISIBLE
                        btnMasuk.visibility = View.VISIBLE
                    }
                } else {
                    binding.apply {
                        btnMasuk.visibility = View.INVISIBLE
                        ivEditAccount.visibility = View.VISIBLE
                        ivLogoutAccount.visibility = View.VISIBLE
                        ivOptionAccount.visibility = View.VISIBLE
                        tvEditAccount.visibility = View.VISIBLE
                        tvLogoutAccount.visibility = View.VISIBLE
                        tvOptionAccount.visibility = View.VISIBLE
                        viewEdit.visibility = View.VISIBLE
                        viewLogout.visibility = View.VISIBLE
                        viewOption.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun dialogLogout(listener: (dialog: AlertDialog) -> Unit) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setCancelable(false)
        builder.setTitle("Attention!")
        builder.setMessage("Apakah anda ingin keluar dari akun ini?")
        builder.setPositiveButton("Iya", null)
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            listener.invoke(dialog)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}