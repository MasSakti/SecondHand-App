package id.co.binar.secondhand.ui.dashboard.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.buyer.product.GetProductResponse
import id.co.binar.secondhand.model.seller.category.GetCategoryResponse
import id.co.binar.secondhand.repository.BuyerRepository
import id.co.binar.secondhand.util.LiveEvent
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val _getProduct = MutableLiveData<Int?>()
    fun getProduct(category: Int? = null) {
        _getProduct.postValue(category)
    }

    private val _getProductBySearch = state.getLiveData<String?>("QUERY_SEARCH")
    fun getProductBySearch(search: String? = null) {
        _getProductBySearch.postValue(search)
    }

    private val _getProductByCategory = state.getLiveData<Int?>("QUERY_CATEGORY")
    fun getProductByCategory(category: Int? = null) {
        _getProductByCategory.postValue(category)
    }

    private val _getTitleCategory = state.getLiveData<String?>("TITLE_CATEGORY")
    val getTitleCategory : LiveData<String?> = _getTitleCategory
    fun getTitleCategory(title: String? = null) {
        _getTitleCategory.postValue(title)
    }

    val getProduct = _getProduct.switchMap {
        buyerRepository.getProduct().asLiveData()
    }

    val getProductBySearch = _getProductBySearch.switchMap {
        buyerRepository.getProduct(search = it).cachedIn(CoroutineScope(Dispatchers.IO)).asLiveData()
    }

    val getProductByCategory = _getProductByCategory.switchMap {
        buyerRepository.getProduct(category = it).cachedIn(CoroutineScope(Dispatchers.IO)).asLiveData()
    }

    val getCategory = buyerRepository.getCategory().asLiveData()
}