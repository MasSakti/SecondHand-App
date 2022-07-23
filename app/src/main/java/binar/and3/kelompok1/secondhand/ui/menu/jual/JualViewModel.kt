package binar.and3.kelompok1.secondhand.ui.menu.jual

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.common.reduceFileImage
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategoryResponse
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JualViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var name: String = ""
    private var description: String = ""
    private var basePrice: String = ""
    private var categoryIds: List<Int> = emptyList()
    private var image: File? = null

    fun onChangeName(name: String) {
        this.name = name
    }
    fun onChangeDescription(description: String) {
        this.description = description
    }
    fun onChangeBasePrice(basePrice: String) {
        this.basePrice = basePrice
    }
    fun onChangeCategoryIds(categoryIds: List<Int>) {
        this.categoryIds = categoryIds
    }
    fun onChangeImage(image: File) {
        this.image = image
    }

    val shouldShowCategory: MutableLiveData<List<GetSellerCategoryResponse>> = MutableLiveData()
    val shouldShowSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    private var addCategory = MutableLiveData<List<String>>()
    val categoryList : LiveData<List<String>> get() = addCategory

    fun getSellerCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = productRepository.getSellerCategory()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowCategory.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    fun addCategory(category: List<String>){
        addCategory.postValue(category)
    }

    fun onValidate() {
        if (name.isEmpty()) {
            shouldShowError.postValue("Nama produk harus diisi")
        } else if (description.isEmpty()) {
            shouldShowError.postValue("Deskripsi harus jelas")
        } else if (basePrice.isEmpty()) {
            shouldShowError.postValue("Kamu harus menentukan harga")
        } else if (categoryIds.isEmpty()) {
            shouldShowError.postValue("Setidaknya kamu harus mengisi satu jenis kategori")
        } else if (image.toString().isEmpty()) {
            shouldShowError.postValue("Mohon beri bukti foto")
        } else {
            image?.let {
                processToUploadProduct(
                    name = name,
                    description = description,
                    basePrice = basePrice,
                    categoryIds = categoryIds,
                    image = it
                )
            }
        }
    }

    private fun processToUploadProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        image: File,
        location: String = "-"
    ) {
        val requestFile = reduceFileImage(image).asRequestBody("image/jpg".toMediaTypeOrNull())
        val nameRequestBody = name.toRequestBody("text/plain".toMediaType())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())
        val basePriceRequestBody = basePrice.toRequestBody("text/plain".toMediaType())
        val imageRequestBody = MultipartBody.Part.createFormData("image", image.name, requestFile)
        val locationRequestBody = location.toRequestBody("text/plain".toMediaType())
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            val result = productRepository.postSellerProduct(
                accessToken = accessToken,
                name = nameRequestBody,
                description = descriptionRequestBody,
                basePrice = basePriceRequestBody,
                image = imageRequestBody,
                categoryIds = categoryIds,
                location = locationRequestBody
            )
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowSuccess.postValue(true)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

}