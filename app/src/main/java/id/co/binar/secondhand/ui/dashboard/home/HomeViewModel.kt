package id.co.binar.secondhand.ui.dashboard.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.binar.secondhand.repository.BuyerRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val buyerRepository: BuyerRepository,
    state: SavedStateHandle
) : ViewModel() {

    fun getCategory() = buyerRepository.getCategory().asLiveData()
    fun getProduct() = buyerRepository.getProduct().asLiveData()
}