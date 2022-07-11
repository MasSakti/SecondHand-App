package binar.and3.kelompok1.secondhand.ui.seller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.and3.kelompok1.secondhand.model.ProductDetailModel
import binar.and3.kelompok1.secondhand.repository.AuthRepository
import binar.and3.kelompok1.secondhand.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductPreviewViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val shouldShowProduct: MutableLiveData<List<ProductDetailModel>> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun getProductById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = productRepository.getBuyerProductById(id = id)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val productDetailResponse = result.body()
                    val productModel = productDetailResponse?.categories?.map { categories ->
                        ProductDetailModel.Categories(
                            id = categories.id.hashCode(),
                            name = categories.name.toString()
                        ).let {
                            ProductDetailModel(
                                id = productDetailResponse.id.hashCode(),
                                name = productDetailResponse.name.toString(),
                                basePrice = productDetailResponse.basePrice.hashCode(),
                                imageUrl = productDetailResponse.imageUrl.toString(),
                                imageName = productDetailResponse.imageName.toString(),
                                location = productDetailResponse.location.toString(),
                                categories = listOf(it),
                                user = productDetailResponse.user.let { user ->
                                    ProductDetailModel.User(
                                        id = user.id.hashCode(),
                                        fullName = user.fullName.toString(),
                                        imageUrl = user.imageUrl.toString(),
                                        city = user.city.toString()
                                    )
                                }
                            )
                        }
                    }
                    shouldShowProduct.postValue(productModel!!)
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }
}