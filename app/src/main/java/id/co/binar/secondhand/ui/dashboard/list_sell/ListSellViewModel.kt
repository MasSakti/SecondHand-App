package id.co.binar.secondhand.ui.dashboard.list_sell

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.data.local.model.SellerProductLocal
import id.co.binar.secondhand.model.ErrorResponse
import id.co.binar.secondhand.model.seller.category.GetCategoryResponse
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
    authRepository: AuthRepository
) : ViewModel() {
    val getAccount = authRepository.getAccount().asLiveData()
}