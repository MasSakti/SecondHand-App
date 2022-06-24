package id.co.binar.secondhand.ui.dashboard.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.buyer.product.GetProductResponseItem
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.repository.BuyerRepository
import id.co.binar.secondhand.util.LiveEvent
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val _queryCategory = MutableLiveData<Int?>()
    val queryCategory: LiveData<Int?> = _queryCategory
    fun queryCategory(category: Int? = null) {
        _queryCategory.postValue(category)
    }

    private val _querySearch = state.getLiveData<String?>("QUERY_SEARCH")
    val querySearch: LiveData<String?> = _querySearch
    fun querySearch(search: String? = null) {
        _querySearch.postValue(search)
    }

    fun getCategory() = buyerRepository.getCategory().asLiveData()

    private val _getProduct = MutableLiveData<Resource<List<GetProductResponseItem>>>()
    val getProduct: LiveData<Resource<List<GetProductResponseItem>>> = _getProduct
    fun getProduct(category: Int?) = CoroutineScope(Dispatchers.IO).launch {
        _getProduct.postValue(Resource.Loading())
        try {
            val response = buyerRepository.getProduct(category = category, search = null)
            if (response.isSuccessful) {
                response.body()?.let {
                    _getProduct.postValue(Resource.Success(it))
                }
            } else {
                throw Exception("Terjadi kesalahan!")
            }
        } catch (ex: Exception) {
            _getProduct.postValue(Resource.Error(ex))
        }
    }

    private val _getSearch = MutableLiveData<Resource<List<GetProductResponseItem>>>()
    val getSearch: LiveData<Resource<List<GetProductResponseItem>>> = _getSearch
    fun getSearch(search: String?) = CoroutineScope(Dispatchers.IO).launch {
        _getSearch.postValue(Resource.Loading())
        try {
            val response = buyerRepository.getProduct(search = search, category = null)
            if (response.isSuccessful) {
                response.body()?.let {
                    _getSearch.postValue(Resource.Success(it))
                }
            } else {
                throw Exception("Terjadi kesalahan!")
            }
        } catch (ex: Exception) {
            _getSearch.postValue(Resource.Error(ex))
        }
    }
}