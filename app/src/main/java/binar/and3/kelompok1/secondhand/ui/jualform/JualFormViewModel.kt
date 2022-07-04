package binar.and3.kelompok1.secondhand.ui.jualform

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.data.api.seller.SellerProductResponse
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class JualFormViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var name: String = ""
    private var description: String = ""
    private var base_price: Long = 0
    private var location: String = ""
    private var image: String = ""
    private var category_ids: ArrayList<Int> = arrayListOf()

    val shouldShowMyProduct: MutableLiveData<SellerProductResponse> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onChangeName(name: String) {
        this.name = name
    }

    fun onChangeDescription(description: String) {
        this.description = description
    }

    fun onChangeBasePrice(basePrice: Long) {
        this.base_price = basePrice
    }

    fun onChangeLocation(location: String) {
        this.location = location
    }

    fun onChangeImage(image: String) {
        this.image = image
    }

    fun onChangeCategoryIds(categoryIds: ArrayList<Int>) {
        this.category_ids = categoryIds
    }


}