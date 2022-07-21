package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.GetDetailOrder.GetDetailOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder.UpdateStatusOrderRequest
import com.tegarpenemuan.secondhandecomerce.data.api.BuyerOrder.UpdateStatusOrder.UpdateStatusOrderResponse
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.deleteproduct.DeleteSellerProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.getProfile.GetProfileResponse
import com.tegarpenemuan.secondhandecomerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DaftarJualViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val shouldShowGetProductSeller: MutableLiveData<List<GetProductResponse>> = MutableLiveData()
    val shouldShowGetSellerOrder: MutableLiveData<List<SellerOrderResponseItem>> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    val showUser: MutableLiveData<GetProfileResponse> = MutableLiveData()

    //val shouldShowUser: MutableLiveData<UserEntity> = MutableLiveData()
    val shouldShowDetailNotif: MutableLiveData<GetDetailOrderResponse> = MutableLiveData()
    val shouldShowUpdateStatusOrdoer: MutableLiveData<UpdateStatusOrderResponse> = MutableLiveData()
    val showDelete: MutableLiveData<DeleteSellerProductResponse> = MutableLiveData()
    val showErrorDeleteProduct: MutableLiveData<String> = MutableLiveData()

    fun getProductSeller() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProductSeller(repository.getToken()!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getProductResponse = response.body()
                    shouldShowGetProductSeller.postValue(getProductResponse!!)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

    fun getOrder(status: String? = "") {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getOrder(repository.getToken()!!, status)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getProductResponse = response.body()
                    shouldShowGetSellerOrder.postValue(getProductResponse!!)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

    //Room
//    fun getUser() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = repository.getUser()
//            withContext(Dispatchers.Main) {
//                shouldShowUser.postValue(
//                    UserEntity(
//                        id = result.id,
//                        full_name = result.full_name,
//                        email = result.email,
//                        phone_number = result.phone_number,
//                        address = result.address,
//                        image_url = result.image_url,
//                        city = result.city
//                    )
//                )
//            }
//        }
//    }
    fun getUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getProfile(repository.getToken()!!)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    //show data
                    val data = result.body()
                    showUser.postValue(data!!)
                } else {
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }

    fun getDetailOrder(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getDetailOrder(repository.getToken()!!, id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getNotificationResponse = response.body()
                    shouldShowDetailNotif.postValue(getNotificationResponse!!)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

    fun updateStatusOrder(id: Int, request: UpdateStatusOrderRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.updateStatusOrder(repository.getToken()!!, id, request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val updateStatusOrder = response.body()
                    shouldShowUpdateStatusOrdoer.postValue(updateStatusOrder!!)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

    fun deleteSellerProduct(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.deleteSellerProduct(repository.getToken()!!, id)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val data = result.body()
                    showDelete.postValue(data!!)
                } else {
                    val data = result.errorBody()
                    showErrorDeleteProduct.postValue(data.toString())
                }
            }
        }
    }
}