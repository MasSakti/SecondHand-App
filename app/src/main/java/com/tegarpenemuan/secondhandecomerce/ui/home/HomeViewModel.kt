package com.tegarpenemuan.secondhandecomerce.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponse
import com.tegarpenemuan.secondhandecomerce.data.api.category.GetCategoryResponseItem
import com.tegarpenemuan.secondhandecomerce.data.api.getProduct.GetProductResponse
import com.tegarpenemuan.secondhandecomerce.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val shouldShowGetProduct: MutableLiveData<List<GetProductResponse>> = MutableLiveData()
    val shouldShowGetCategory: MutableLiveData<List<GetCategoryResponseItem>> = MutableLiveData()

    fun getProduct(status: String? = "", category_id: Int? = null, search: String? = "") {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getProduct(status, category_id, search)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getProductResponse = response.body()
                    shouldShowGetProduct.postValue(getProductResponse)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }

    fun getCategory() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getCategory()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getCategoryResponse = response.body()
                    shouldShowGetCategory.postValue(getCategoryResponse)
                } else {
                    //shouldShowError.postValue("Request get Profile Tidak Failed" + response.code())
                }
            }
        }
    }
}