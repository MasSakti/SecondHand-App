package binar.and3.kelompok1.secondhand.ui.menu.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buyerProductRepository: ProductRepository
) : ViewModel() {

    val shouldShowBuyerProduct: MutableLiveData<List<BuyerProductResponse>> = MutableLiveData()

    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded() {
        tempBuyerProduct()
    }

    private fun getBuyerProduct() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = buyerProductRepository.getBuyerProduct()
//            withContext(Dispatchers.Main) {
//                if (result.isSuccessful) {
//                    val buyerProductResponse = result.body()
//                    buyerProductResponse?.let {
//                        val product = BuyerEntity(
//                            id = it.id.hashCode(),
//                            name = it.name.orEmpty(),
//                            base_price = it.basePrice.hashCode(),
//                            imageUrl = it.imageUrl.orEmpty(),
//                            imageName = it.imageName.orEmpty(),
//                            location = it.location.orEmpty(),
//                            userId = it.userId.hashCode()
//                        )
//                        insertHomeProduct(product)
//                    }
//                }
//            }
//        }
    }
    private fun insertHomeProduct(buyerEntity: BuyerEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = buyerProductRepository.insertProductToLocal(buyerEntity = buyerEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    productFromDatabase()
                } else {
                    shouldShowError.postValue("Gagal insert ke dalam database")
                }
            }
        }
    }

    private fun productFromDatabase() {
    }

    // Sementara get dari online dulu
    private fun tempBuyerProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = buyerProductRepository.getBuyerProduct()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowBuyerProduct.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

}