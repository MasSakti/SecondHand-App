package id.co.binar.secondhand.ui.dashboard.list_sell.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.model.seller.order.GetOrderResponse
import javax.inject.Inject

@HiltViewModel
class InfoBidSuccessViewModel @Inject constructor(
    state: SavedStateHandle
) : ViewModel() {
    private val _args = state.getLiveData<GetOrderResponse>("ARGS_ORDER")
    val args: LiveData<GetOrderResponse> = _args
    fun args(value: GetOrderResponse) {
        _args.postValue(value)
    }
}