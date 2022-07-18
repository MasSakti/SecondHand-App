package binar.and3.kelompok1.secondhand.ui.seller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductByIdResponse
import binar.and3.kelompok1.secondhand.model.ProductDetailModel
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductPreviewViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val shouldShowProduct: MutableLiveData<GetProductByIdResponse> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun getProductById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = productRepository.getBuyerProductById(id = id)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val productDetailResponse = result.body()
                    shouldShowProduct.postValue(productDetailResponse)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }
}