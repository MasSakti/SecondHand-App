package id.co.binar.secondhand.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
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
    authRepository: AuthRepository
) : ViewModel() {
    val getAccount = authRepository.getAccount().asLiveData()

    private val _getProductById = MutableLiveData<Resource<GetProductResponse>>()
    val getProductById: LiveData<Resource<GetProductResponse>> = _getProductById
    fun getProductById(product_id: Int) = CoroutineScope(Dispatchers.IO).launch {
        buyerRepository.getProductById(product_id).collectLatest {
            _getProductById.postValue(it)
        }
    }
}