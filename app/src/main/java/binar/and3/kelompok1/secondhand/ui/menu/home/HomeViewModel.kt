package binar.and3.kelompok1.secondhand.ui.menu.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.buyer.BuyerProductResponse
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerBannerResponse
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerCategoryResponse
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
    private val buyerProductRepository: ProductRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val shouldShowBanner: MutableLiveData<List<GetSellerBannerResponse>> = MutableLiveData()
    val shouldShowBuyerProductByCategory: MutableLiveData<List<BuyerProductResponse>> =
        MutableLiveData()
    val tempShouldShowCategory: MutableLiveData<List<GetSellerCategoryResponse>> = MutableLiveData()

    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded() {
        getBanner()
        getSellerCategory()
        getBuyerProduct(categoryId = "", search = "")
    }

    private fun getBanner() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = productRepository.getSellerBanner()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowBanner.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    private fun getSellerCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = productRepository.getSellerCategory()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    tempShouldShowCategory.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    fun getBuyerProduct(categoryId: String, search: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = productRepository.getBuyerProductByCategory(categoryId = categoryId, search = search)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowBuyerProductByCategory.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

}