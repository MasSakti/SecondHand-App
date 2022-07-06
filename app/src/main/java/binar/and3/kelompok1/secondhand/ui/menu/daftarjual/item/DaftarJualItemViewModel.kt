package binar.and3.kelompok1.secondhand.ui.menu.daftarjual.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductResponse
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DaftarJualItemViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val shouldShowMyProduct: MutableLiveData<List<GetProductResponse>> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded() {
        getMyProduct()
    }

    private fun getMyProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            println("Akses token: $accessToken")
            val result = productRepository.getSellerProduct(accessToken = accessToken)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val body = result.body()
                    shouldShowMyProduct.postValue(body)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }
}