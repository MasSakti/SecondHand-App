package id.co.binar.secondhand.ui.dashboard.list_sell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.data.local.model.SellerProductLocal
import id.co.binar.secondhand.repository.AuthRepository
import id.co.binar.secondhand.repository.SellerRepository
import id.co.binar.secondhand.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSellViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sellerRepository: SellerRepository
) : ViewModel() {
    fun getAccount() = authRepository.getAccount().asLiveData()

    private val _getProduct = MutableLiveData<Resource<List<SellerProductLocal>>>()
    val getProduct: LiveData<Resource<List<SellerProductLocal>>> = _getProduct
    fun getProduct() = CoroutineScope(Dispatchers.IO).launch {
        sellerRepository.getProduct().collectLatest {
            _getProduct.postValue(it)
        }
    }
}