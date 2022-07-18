package com.tegarpenemuan.secondhandecomerce.ui.buyerOrder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.createOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.createOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProductDetails.GetProductDetailsResponse
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BuyerOrderViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val shouldShowGetProductDetails: MutableLiveData<GetProductDetailsResponse> = MutableLiveData()
    val shouldShowResponsBid: MutableLiveData<createOrderResponse> = MutableLiveData()
    val shouldShowResponsError: MutableLiveData<String> = MutableLiveData()

    var order_Id: Int? = null

    fun getOrderId(orderId: Int) {
        order_Id = orderId
    }

    fun getProductDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProductId(id = order_Id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    shouldShowGetProductDetails.postValue(response.body())
                } else {
                    shouldShowResponsError.postValue("Terjadi Kesalahan" + response.code())
                }
            }
        }
    }

    fun createOrder(product_id:Int, bid_price: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.createOrder(
                repository.getToken()!!, createOrderRequest(
                    product_id = product_id,
                    bid_price = bid_price
                )
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    shouldShowResponsBid.postValue(response.body())
                } else {
                    shouldShowResponsError.postValue("Terjadi Kesalahan: " + response.code())
                }
            }
        }
    }

}

