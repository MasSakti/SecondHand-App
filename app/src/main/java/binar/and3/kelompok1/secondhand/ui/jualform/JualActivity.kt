package binar.and3.kelompok1.secondhand.ui.jualform

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.doOnAttach
import androidx.core.widget.doAfterTextChanged
import binar.and3.kelompok1.secondhand.databinding.ActivityJualBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class JualActivity : AppCompatActivity() {
    lateinit var binding: ActivityJualBinding
    private val viewModel: JualFormViewModel by viewModels()
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityJualBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE


    }

    private fun bindView() {
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
                uri.let {
                    val type = Activity().contentResolver.getType(it)

                    val tempFile = File.createTempFile("temp-", null, null)
                    val inputStream = Activity().contentResolver.openInputStream(uri)

                    tempFile.outputStream().use { inputStream?.copyTo(it) }

                    val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                    val body = MultipartBody.Part.createFormData("image", tempFile.name, requestBody)

                    // lanjut tambah ViewModel untuk upload gambar

                }
            }


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
        binding.ivFotoProduk.doOnAttach {
            viewModel.onChangeImage(it.toString())
        }
    }

}