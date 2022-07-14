package com.tegarpenemuan.secondhandecomerce.ui.buyer6

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.getProductDetails.GetProductDetailsResponse
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
//class Buyer6ViewModel @Inject constructor(
//    private val repository: AuthRepository
//) : ViewModel() {
//    val shouldShowGetProductId: MutableLiveData<List<GetProductResponse>> = MutableLiveData()
//}
//private fun getMovieUpcoming(){
//    CoroutineScope(Dispatchers.IO).launch {
//        val result = repository.getProductId()
//        withContext(Dispatchers.Main) {
//            if (result.isSuccessful) {
//                shouldShowGetProductId.postValue(result.body())
//            } else {
//                shouldShowError.postValue(result.errorBody().toString())
//            }
//        }
//    }
//}
class Buyer6ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val shouldShowGetProductDetails: MutableLiveData<GetProductDetailsResponse> = MutableLiveData()
    var order_Id: Int? = null

    fun getOrderId(orderId : Int) {
        order_Id = orderId
    }

    fun getProductDetails(){
        CoroutineScope(Dispatchers.IO).launch{
            val response = repository.getProductId(id = order_Id!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    shouldShowGetProductDetails.postValue(response.body())
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

}

