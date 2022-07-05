package id.co.binar.secondhand.ui.info_bid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.seller.order.GetOrderResponse
import id.co.binar.secondhand.model.seller.order.UpdateOrderRequest
import id.co.binar.secondhand.repository.SellerRepository
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoBidViewModel @Inject constructor(
    private val sellerRepository: SellerRepository
) : ViewModel() {
    private val _response = MutableLiveData<Resource<GetOrderResponse>>()
    val response : LiveData<Resource<GetOrderResponse>> = _response

    fun getOrderById(id: Int) = CoroutineScope(Dispatchers.IO).launch {
        sellerRepository.getOrderById(id).collectLatest {
            _response.postValue(it)
        }
    }

    fun updateOrder(id: Int, field: UpdateOrderRequest) = CoroutineScope(Dispatchers.IO).launch {
        sellerRepository.updateOrder(id, field).collectLatest {
            _response.postValue(it)
        }
    }
}