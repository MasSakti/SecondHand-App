package id.co.binar.secondhand.ui.dashboard.home

import androidx.lifecycle.*
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

    private val _getSearch = state.getLiveData<String?>("QUERY_SEARCH")
    fun getSearch(search: String? = null) {
        _getSearch.postValue(search)
    }

    val getProduct = _getProduct.switchMap {
        buyerRepository.getProduct(category = it).asLiveData()
    }

    val getSearch = _getSearch.switchMap {
        buyerRepository.getProduct(search = it).asLiveData()
    }

    val getCategory = buyerRepository.getCategory().asLiveData()
}