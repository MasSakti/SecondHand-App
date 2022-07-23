package binar.and3.kelompok1.secondhand.ui.buyer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.buyer.PostBuyerBidRequest
import binar.and3.kelompok1.secondhand.data.api.seller.GetProductByIdResponse
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var bid: String = ""

    val shouldShowProduct: MutableLiveData<GetProductByIdResponse> = MutableLiveData()
    val shouldShowSuccessBiding: MutableLiveData<String> = MutableLiveData()
    val shouldOpenHome: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onChangeBidPrice(bid: String) {
        this.bid = bid
    }

    fun onValidate(productId: Int) {
        if (bid.isEmpty()) {
            shouldShowError.postValue("Mohon isi harga tawar kamu")
        } else {
            postBuyerBid(productId = productId)
        }
    }

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

    fun postBuyerBid(productId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            val request = PostBuyerBidRequest(
                productId = productId,
                bidPrice = bid.toInt()
            )
            val result = productRepository.postBuyerBid(
                accessToken = accessToken,
                request
            )
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowSuccessBiding.postValue("Tawaranmu berhasil dikirim. Mohon tunggu response dari Seller!")
                    shouldOpenHome.postValue(true)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }
}