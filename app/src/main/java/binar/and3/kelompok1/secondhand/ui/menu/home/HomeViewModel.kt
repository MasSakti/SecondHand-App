package binar.and3.kelompok1.secondhand.ui.menu.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProduct
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

    val shouldShowBuyerProduct: MutableLiveData<BuyerProduct> = MutableLiveData()

    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded() {
        getBuyerProduct()
    }

    private fun getBuyerProduct() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = buyerProductRepository.getProduct()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val buyerProductResponse = result.body()
                    buyerProductResponse?.response?.map {
                        val product = BuyerEntity(
                            id = it.id.hashCode(),
                            name = it.name.orEmpty(),
                            base_price = it.basePrice.hashCode(),
                            imageUrl = it.imageUrl.orEmpty(),
                            imageName = it.imageName.orEmpty(),
                            location = it.location.orEmpty(),
                            userId = it.userId.hashCode()
                        )
                        insertHomeProduct(product)
                    }
                }
            }
        }
    }
    private fun insertHomeProduct(buyerEntity: BuyerEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = buyerProductRepository.insertProduct(buyerEntity = buyerEntity)
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
}