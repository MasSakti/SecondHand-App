package binar.and3.kelompok1.secondhand.ui.jualform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategoryResponse
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
    private var basePrice: Int = 0
    private var categoryIds: List<Int> = emptyList()
    private var description: String = ""
    private var image: MultipartBody.Part? = null

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowCategory: MutableLiveData<List<GetSellerCategoryResponse>> = MutableLiveData()
    val shouldOpenDaftarJual: MutableLiveData<Boolean> = MutableLiveData()
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

    fun onChangeCategoryIds(categoryIds: List<Int>) {
        this.categoryIds = categoryIds
    }

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
}