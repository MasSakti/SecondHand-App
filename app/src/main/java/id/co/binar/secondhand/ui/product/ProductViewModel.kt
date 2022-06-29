package id.co.binar.secondhand.ui.product

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.buyer.order.AddOrderRequest
import id.co.binar.secondhand.model.buyer.order.GetOrderResponse
import id.co.binar.secondhand.model.buyer.product.GetProductResponse
import id.co.binar.secondhand.repository.AuthRepository
import id.co.binar.secondhand.repository.BuyerRepository
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository,
    authRepository: AuthRepository,
    state: SavedStateHandle
) : ViewModel() {
    val getAccount = authRepository.getAccount().asLiveData()

    private val _passToBiding = state.getLiveData<GetProductResponse>("STATE_PRODUCT")
    val passToBiding: LiveData<GetProductResponse> = _passToBiding
    fun passToBiding(item: GetProductResponse) {
        _passToBiding.postValue(item)
    }

    private val _getProductById = state.getLiveData<Int>("STATE_ID_PRODUCT")
    fun getProductById(product_id: Int) {
        _getProductById.postValue(product_id)
    }

    val getProductById = _getProductById.switchMap {
        buyerRepository.getProductById(it).asLiveData()
    }

    val getProductId: LiveData<Int> = _getProductById

    private val _newOrder = MutableLiveData<Resource<GetOrderResponse>>()
    val newOrder: LiveData<Resource<GetOrderResponse>> = _newOrder
    fun newOrder(field: AddOrderRequest) = CoroutineScope(Dispatchers.IO).launch {
        buyerRepository.newOrder(field).collectLatest {
            _newOrder.postValue(it)
        }
    }
}