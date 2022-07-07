package binar.and3.kelompok1.secondhand.ui.jualform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.seller.PostProductRequest
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class JualFormViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var name: String = ""
    private var description: String = ""
    private var basePrice: Int = 0
    private var location: String = ""
    private var image: String = ""
    private var categoryIds: ArrayList<Int> = arrayListOf()

    val shouldOpenDaftarJual: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onChangeName(name: String) {
        this.name = name
    }

    fun onChangeDescription(description: String) {
        this.description = description
    }

    fun onChangeBasePrice(basePrice: Int) {
        this.basePrice = basePrice
    }

    fun onChangeLocation(location: String) {
        this.location = location
    }

    fun onChangeImage(image: String) {
        this.image = image
    }

    fun onChangeCategoryIds(categoryIds: ArrayList<Int>) {
        this.categoryIds = categoryIds
    }

    fun onValidate(image: MultipartBody.Part) {
        if (name.isEmpty()) {
            shouldShowError.postValue("Nama Barang harus diisi")
        } else if (description.isEmpty()) {
            shouldShowError.postValue("Deskripsi harus jelas")
        } else if (basePrice.toString().isEmpty()) {
            shouldShowError.postValue("Harga barang harus diisi")
        } else if (location.isEmpty()) {
            shouldShowError.postValue("Isi lokasimu")
        } else if (image.toString().isEmpty()) {
            shouldShowError.postValue("Kamu harus mengisi bukti gambar dengan jelas")
        } else if (categoryIds.isEmpty()) {
            shouldShowError.postValue("Kategori harus diisi")
        } else {
            processToUploadProduct(image = image)
        }
    }

    fun processToUploadProduct(image: MultipartBody.Part) {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val accessToken = authRepository.getToken().toString()
            val request = PostProductRequest(
                name = name,
                description = description,
                basePrice = basePrice,
                categoryIds = categoryIds,
                location = location,
                image = image
            )
            val result =
                productRepository.postSellerProduct(request = request, accessToken = accessToken)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldOpenDaftarJual.postValue(true)
                } else {
                    shouldShowError.postValue(
                        "Gagal menambahkan barang. Error: " + result.errorBody().toString()
                    )
                }
            }
        }
    }


}