package com.tegarpenemuan.secondhandecomerce.ui.daftarjual

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.Product.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.data.api.SellerOrder.SellerOrderResponseItem
import com.tegarpenemuan.secondhandecomerce.data.local.UserEntity
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
    val shouldShowUser: MutableLiveData<UserEntity> = MutableLiveData()

    fun getProductSeller(){
        CoroutineScope(Dispatchers.IO).launch{
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

    fun getUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getUser()
            withContext(Dispatchers.Main) {
                shouldShowUser.postValue(
                    UserEntity(
                        id = result.id,
                        full_name = result.full_name,
                        email = result.email,
                        phone_number = result.phone_number,
                        address = result.address,
                        image_url = result.image_url,
                        city = result.city
                    )
                )
            }
        }
    }
}