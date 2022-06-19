package id.co.binar.secondhand.ui.product_add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.auth.AddAuthRequest
import id.co.binar.secondhand.model.buyer.product.GetProductResponseItem
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.model.seller.product.AddProductRequest
import id.co.binar.secondhand.model.seller.product.AddProductResponse
import id.co.binar.secondhand.repository.AuthRepository
import id.co.binar.secondhand.repository.SellerRepository
import id.co.binar.secondhand.util.LiveEvent
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProductAddViewModel @Inject constructor(
    private val sellerRepository: SellerRepository,
    state: SavedStateHandle
) : ViewModel() {
    fun getTokenId() = runBlocking { sellerRepository.store().getTokenId() }

    private val _list = state.getLiveData<MutableList<GetCategoryResponseItem>>("LIST_CATEGORY_PRODUCT")
    val list: LiveData<MutableList<GetCategoryResponseItem>> = _list
    fun list(list: MutableList<GetCategoryResponseItem>) {
        _list.postValue(list.distinctBy { it.id }.toMutableList())
    }

    private val _lastList = state.getLiveData<MutableList<GetCategoryResponseItem>>("LAST_LIST_CATEGORY_PRODUCT")
    val lastList: LiveData<MutableList<GetCategoryResponseItem>> = _lastList
    fun lastList(list: MutableList<GetCategoryResponseItem>) {
        _lastList.postValue(list.distinctBy { it.id }.toMutableList())
    }

    private val _categoryProduct = MutableLiveData<Resource<List<GetCategoryResponseItem>>>()
    val categoryProduct: LiveData<Resource<List<GetCategoryResponseItem>>> = _categoryProduct
    fun categoryProduct() = CoroutineScope(Dispatchers.IO).launch {
        _categoryProduct.postValue(Resource.Loading())
        try {
            val response = sellerRepository.getCategory(getTokenId().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    _categoryProduct.postValue(Resource.Success(it))
                }
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            _categoryProduct.postValue(Resource.Error(code = null, message = ex.message.toString()))
            throw ex
        }
    }

    private val _addProduct = MutableLiveData<Resource<AddProductResponse>>()
    val addProduct: LiveData<Resource<AddProductResponse>> = _addProduct
    fun addProduct(field: AddProductRequest, image: MultipartBody.Part) = CoroutineScope(Dispatchers.IO).launch {
        _addProduct.postValue(Resource.Loading())
        try {
            val response = sellerRepository.addProduct(field, image)
            if (response.isSuccessful) {
                response.body()?.let {
                    _addProduct.postValue(Resource.Success(it))
                }
            } else if (response.code() == 400) {
                throw Exception("Bad Request")
            } else if (response.code() == 403) {
                throw Exception("Harus masuk akun terlebih dahulu")
            } else {
                throw Exception("Terjadi kesalahan")
            }
        } catch (ex: Exception) {
            _addProduct.postValue(Resource.Error(code = null, message = ex.message.toString()))
        }
    }
}
