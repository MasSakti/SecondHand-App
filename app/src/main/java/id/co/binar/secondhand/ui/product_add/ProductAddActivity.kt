package id.co.binar.secondhand.ui.product_add

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import com.github.dhaval2404.imagepicker.ImagePicker
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import id.co.binar.secondhand.R
import id.co.binar.secondhand.databinding.ActivityProductAddBinding
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.model.seller.product.AddProductRequest
import id.co.binar.secondhand.model.seller.product.UpdateProductByIdRequest
import id.co.binar.secondhand.ui.login.LoginActivity
import id.co.binar.secondhand.ui.product_add.dialog.CategoryDialogFragment
import id.co.binar.secondhand.ui.product_add.dialog.TAG_CATEGORY_DIALOG
import id.co.binar.secondhand.util.*
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

const val ARGS_PRODUCT_EDIT = "EDIT_PRODUCT"

@AndroidEntryPoint
class ProductAddActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityProductAddBinding
    private val viewModel by viewModels<ProductAddViewModel>()
    private var chooseList = mutableListOf<GetCategoryResponseItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Lengkapi Detail Produk"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (viewModel.getTokenId().isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        if (intent.hasExtra(ARGS_PRODUCT_EDIT)) {
            viewModel.getIdProduct(intent.getIntExtra(ARGS_PRODUCT_EDIT, -1))
        }

        viewModel.editProduct.observe(this) {
            when (it) {
                is Resource.Success -> {
                    this.onToast("${it.data?.name} berhasil diupdate")
                    onBackPressed()
                }
                is Resource.Loading -> {
                    this.onToast("Mohon menunggu...")
                }
                is Resource.Error -> {
                    this.onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }

        viewModel.getProductById.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.categories?.forEach {
                        chooseList.add(
                            GetCategoryResponseItem(
                                name = it.name,
                                id = it.id,
                                check = true
                            )
                        )
                    }
                    viewModel.categoryProduct.observe(this) {
                        val lastList = mutableListOf<GetCategoryResponseItem>()
                        lastList.addAll(chooseList)
                        lastList.addAll(it.data.castFromLocalToRemote())
                        lastList.sortBy { it.id }
                        viewModel.lastList(lastList.distinctBy { it.id }.toMutableList())
                    }
                    binding.apply {
                        val loader = ImageLoader(this@ProductAddActivity)
                        val req = ImageRequest.Builder(this@ProductAddActivity)
                            .data(it.data?.imageUrl)
                            .target {
                                val drawable = it.toBitmap()
                                viewModel.bitmap(drawable)
                            }
                            .build()
                        loader.enqueue(req)
                        txtInputLayoutTitle.setText(it.data?.name)
                        txtInputLayoutPrice.setText(it.data?.basePrice.toString())
                        txtInputLocation.setText(it.data?.location)
                        txtInputLayoutDescription.setText(it.data?.description)
                        txtInputLayoutCategory.setText(it.data?.categories?.toNameOnly())
                    }
                }
                is Resource.Loading -> this.onToast("Mohon menunggu...")
                is Resource.Error -> this.onSnackError(binding.root, it.error?.message.toString())
            }
        }

        viewModel.bitmap.observe(this) {
            it?.let { bmp ->
                binding.imgView.setImageBitmap(bmp)
            }
        }
        viewModel.list.observe(this) {
            binding.txtInputLayoutCategory.setText(it.toNameOnly())
            chooseList = it
        }
        viewModel.addProduct.observe(this) {
            when (it) {
                is Resource.Success -> {
                    this.onToast("${it.data?.name} berhasil ditambahkan")
                    onBackPressed()
                }
                is Resource.Loading -> {
                    this.onToast("Mohon menunggu...")
                }
                is Resource.Error -> {
                    this.onSnackError(binding.root, it.error?.message.toString())
                }
            }
        }
    }

    private fun bindView() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)

        binding.imgView.setOnClickListener {
            requestCameraAndWriteExternalPermission(this)
        }

        binding.btnSave.setOnClickListener {
            onValidate()
        }

        binding.txtInputLayoutCategory.setOnClickListener {
            val dialog = CategoryDialogFragment()
            dialog.show(supportFragmentManager, TAG_CATEGORY_DIALOG)
            viewModel.list(chooseList)
        }

        binding.txtInputLayoutPrice.addTextChangedListener(MoneyTextWatcher(binding.txtInputLayoutPrice))
    }

    private val choosePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val resultCode = it.resultCode
        val data = it.data
        try {
            when (resultCode) {
                RESULT_OK -> {
                    data?.let { intent ->
                        binding.imgView.setImageURI(intent.data)
                        val drawable = binding.imgView.drawable.toBitmap()
                        viewModel.bitmap(drawable)
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    throw Exception(ImagePicker.getError(data))
                }
            }
        } catch (ex: Exception) {
            this@ProductAddActivity.onToast(ex.message.toString())
        }
    }

    private fun choosePhotoPermission() {
        ImagePicker.with(this)
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .cropSquare()
            .compress(2048)
            .maxResultSize(2048, 2048)
            .createIntent {
                choosePhoto.launch(it)
            }
    }

    private fun onValidate() {
        validator(this) {
            mode = Mode.SINGLE
            listener = onProductSubmit
            validate(
                generalValid(binding.etTitleProduct),
                priceValid(binding.etPriceProduct),
                generalValid(binding.etCategoriProduct),
                generalValid(binding.etInputLayoutLocation),
                generalValid(binding.etDescription)
            )
        }
    }

    private val onProductSubmit = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            val bitmap = viewModel.bitmap.value
            if (bitmap == null) {
                this@ProductAddActivity.onSnackError(binding.root, "File gambar tidak boleh kosong!")
            } else {
                if (intent.hasExtra(ARGS_PRODUCT_EDIT)) {
                    viewModel.editProduct(
                        intent.getIntExtra(ARGS_PRODUCT_EDIT, -1),
                        UpdateProductByIdRequest(
                            name = binding.txtInputLayoutTitle.text.toString(),
                            basePrice = MoneyTextWatcher.parseCurrencyValue(binding.txtInputLayoutPrice.text.toString()).toLong(),
                            categoryIds = chooseList.toIntOnly(),
                            location = binding.txtInputLocation.text.toString(),
                            description = binding.txtInputLayoutDescription.text.toString()
                        ),
                        this@ProductAddActivity.buildImageMultipart("image", bitmap)
                    )
                } else {
                    viewModel.addProduct(
                        AddProductRequest(
                            name = binding.txtInputLayoutTitle.text.toString(),
                            basePrice = MoneyTextWatcher.parseCurrencyValue(binding.txtInputLayoutPrice.text.toString()).toLong(),
                            categoryIds = chooseList.toIntOnly(),
                            location = binding.txtInputLocation.text.toString(),
                            description = binding.txtInputLayoutDescription.text.toString()
                        ),
                        this@ProductAddActivity.buildImageMultipart("image", bitmap)
                    )
                }
            }
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, listOf(perms[0]))) {
            SettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == PERMISSION_CAMERA_AND_WRITE_EXTERNAL) {
            choosePhotoPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}