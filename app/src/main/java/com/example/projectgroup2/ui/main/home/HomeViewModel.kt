package com.example.projectgroup2.ui.main.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectgroup2.data.api.main.buyer.product.GetProductResponse
import com.example.projectgroup2.data.api.main.buyer.product.ProductResponse
import com.example.projectgroup2.data.api.main.category.CategoryResponse
import com.example.projectgroup2.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val repo: ProductRepository): ViewModel() {
    val showProductBuyer: MutableLiveData<List<GetProductResponse>> = MutableLiveData()
    val showCategory: MutableLiveData<List<CategoryResponse>> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showEmpty: MutableLiveData<Boolean> = MutableLiveData()
    val showShimmer: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("NullSafeMutableLiveData")
    fun getProductBuyer(status: String, categoryId: String, search: String){
        CoroutineScope(Dispatchers.IO).launch {
            showEmpty.postValue(false)
            showShimmer.postValue(true)
            val result = repo.getProductBuyer(status, categoryId, search)
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    if(data!!.isEmpty()){
                        showEmpty.postValue(true)
                    }
                    showShimmer.postValue(true)
                    showProductBuyer.postValue(data)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                    showShimmer.postValue(false)
                    showEmpty.postValue(false)
                }
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getCategory(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getCategory()
            withContext(Dispatchers.Main){
                if (result.isSuccessful){
                    //show data
                    val data = result.body()
                    showCategory.postValue(data)
                }else{
                    //show error
                    val data = result.errorBody()
                    showError.postValue(data.toString())
                }
            }
        }
    }
}