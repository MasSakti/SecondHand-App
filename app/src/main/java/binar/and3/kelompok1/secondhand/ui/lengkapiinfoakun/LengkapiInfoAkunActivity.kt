package binar.and3.kelompok1.secondhand.ui.lengkapiinfoakun

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import binar.and3.kelompok1.secondhand.common.uriToFile
import binar.and3.kelompok1.secondhand.databinding.ActivityLengkapiInfoAkunBinding
import binar.and3.kelompok1.secondhand.ui.MenuActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class LengkapiInfoAkunActivity : AppCompatActivity() {
    lateinit var binding: ActivityLengkapiInfoAkunBinding
    private val viewModel: LengkapiInfoAkunViewModel by viewModels()

    private var uri: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLengkapiInfoAkunBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.statusBarColor = Color.WHITE

        viewModel.onViewLoaded()
        bindViewModel()
        bindView()
    }

    private fun bindView() {
        binding.ivImageAccount.setOnClickListener {
            openImagePicker()
        }
        binding.etName.doAfterTextChanged {
            viewModel.onChangeFullName(it.toString())
        }
        binding.etKota.doAfterTextChanged {
            viewModel.onChangeCity(it.toString())
        }
        binding.etAlamat.doAfterTextChanged {
            viewModel.onChangeAddress(it.toString())
        }
        binding.etPhone.doAfterTextChanged {
            viewModel.onChangePhoneNumber(it.toString())
        }
        binding.btnSimpan.setOnClickListener {
            viewModel.onValidate()
        }
        binding.ivBtnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowProfile.observe(this) {
            Glide.with(applicationContext)
                .load(it.imageUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
                .into(binding.ivImageAccount)

            binding.etName.setText(it.full_name)
            binding.etAlamat.setText(it.address)
            binding.etKota.setText(it.city)
            binding.etPhone.setText("${it.phoneNumber}")
        }
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldShowSuccess.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.GREEN)
            snackbar.show()
        }
    }

    private fun loadImage(uri: Uri) {
        binding.apply {
            Glide.with(binding.root)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivImageAccount)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult  ->
            val resultCode = result.resultCode
            val data = result.data
            when(resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data
                    uri = fileUri.toString()
                    if (fileUri != null) {
                        loadImage(fileUri)
                        viewModel.onChangeImageUrl(uriToFile(Uri.parse(uri), this))
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        this,
                        "Error: + ${ImagePicker.getError(data)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun openImagePicker() {
        ImagePicker.with(this)
            .crop()
            .saveDir(
                File(
                    this.externalCacheDir, "ImagePicker"
                )
            )
            .compress(1024)
            .maxResultSize(500,500)
            .createIntent {
                startForProfileImageResult.launch(it)
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}