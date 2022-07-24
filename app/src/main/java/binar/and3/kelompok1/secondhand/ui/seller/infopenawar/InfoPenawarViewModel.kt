package binar.and3.kelompok1.secondhand.ui.seller.infopenawar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.seller.GetSellerOderByIdResponse
import binar.and3.kelompok1.secondhand.data.api.seller.PatchStatusRequest
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfoPenawarViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    val shouldShowOrder: MutableLiveData<GetSellerOderByIdResponse> = MutableLiveData()
    val shouldShowSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldOpenInfoPenawar: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded(id: Int) {
        getSellerOderById(id = id)
    }

    private fun getSellerOderById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            val result = productRepository.getSellerOderById(accessToken = accessToken, id = id)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowOrder.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    fun patchSellerOrderById(id: Int, status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            val result = productRepository.patchSellerOderById(
                accessToken = accessToken,
                id = id,
                PatchStatusRequest(
                    status = status
                )
            )
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowSuccess.postValue("Yeay kamu berhasil mendapat harga yang sesuai")
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    fun patchSellerProductById(id: Int, status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val accessToken = authRepository.getToken().toString()
            val result = productRepository.patchSellerProductById(
                accessToken = accessToken,
                id = id,
                patchStatusRequest = PatchStatusRequest(
                    status = status
                )
            )
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldOpenInfoPenawar.postValue(true)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }
}