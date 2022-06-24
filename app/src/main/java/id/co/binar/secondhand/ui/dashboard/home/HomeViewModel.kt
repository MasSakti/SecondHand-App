package id.co.binar.secondhand.ui.dashboard.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.buyer.product.GetProductResponseItem
import id.co.binar.secondhand.repository.BuyerRepository
import id.co.binar.secondhand.util.LiveEvent
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository
) : ViewModel() {

    private val _query = LiveEvent<Int?>()
    val query: LiveData<Int?> = _query
    fun query(query: Int?) = _query.postValue(query)

    fun getCategory() = buyerRepository.getCategory().asLiveData()

    private val _getProduct = MutableLiveData<Resource<List<GetProductResponseItem>>>()
    val getProduct: LiveData<Resource<List<GetProductResponseItem>>> = _getProduct
    fun getProduct(category: Int?) = CoroutineScope(Dispatchers.IO).launch {
        _getProduct.postValue(Resource.Loading())
        try {
            val response = buyerRepository.getProduct(category, null)
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
}