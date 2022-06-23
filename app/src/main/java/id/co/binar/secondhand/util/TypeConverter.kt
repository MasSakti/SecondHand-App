package id.co.binar.secondhand.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.binar.secondhand.data.local.model.BuyerProductLocal
import id.co.binar.secondhand.data.local.model.SellerCategoryLocal
import id.co.binar.secondhand.model.buyer.product.CategoriesItem
import id.co.binar.secondhand.model.buyer.product.GetProductResponseItem
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem

class TypeConverter {
    @TypeConverter
    fun fromListToString(list: List<CategoriesItem>?) : String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String?) : List<CategoriesItem>? {
        val list = object : TypeToken<List<CategoriesItem>?>() {}.type
        return Gson().fromJson(string, list)
    }
}

fun List<GetCategoryResponseItem>?.castFromRemoteToLocal(): List<SellerCategoryLocal> {
    val list = mutableListOf<SellerCategoryLocal>()
    this?.forEach {
        list.add(
            SellerCategoryLocal(
                name = it.name,
                id = it.id!!,
            )
        )
    }
    return list
}

fun List<SellerCategoryLocal>?.castFromLocalToRemote(): List<GetCategoryResponseItem> {
    val list = mutableListOf<GetCategoryResponseItem>()
    this?.forEach {
        list.add(
            GetCategoryResponseItem(
                name = it.name,
                id = it.id
            )
        )
    }
    return list
}

fun List<GetProductResponseItem>?.castFromProductRemoteToProductLocal(): List<BuyerProductLocal> {
    val list = mutableListOf<BuyerProductLocal>()
    this?.forEach {
        list.add(
            BuyerProductLocal(
                imageName = it.imageName,
                userId = it.userId,
                imageUrl = it.imageUrl,
                name = it.name,
                description = it.description,
                basePrice = it.basePrice,
                location = it.location,
                id = it.id!!,
                categories = it.categories
            )
        )
    }
    return list
}

fun List<BuyerProductLocal>?.castFromProductLocalToProductRemote(): List<GetProductResponseItem> {
    val list = mutableListOf<GetProductResponseItem>()
    this?.forEach {
        list.add(
            GetProductResponseItem(
                imageName = it.imageName,
                userId = it.userId,
                imageUrl = it.imageUrl,
                name = it.name,
                description = it.description,
                basePrice = it.basePrice,
                location = it.location,
                id = it.id,
                categories = it.categories
            )
        )
    }
    return list
}