package id.co.binar.secondhand.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.binar.secondhand.data.local.model.SellerCategoryLocal
import id.co.binar.secondhand.data.local.model.SellerProductLocal
import id.co.binar.secondhand.model.seller.category.GetCategoryResponseItem
import id.co.binar.secondhand.model.seller.product.CategoriesItem
import id.co.binar.secondhand.model.seller.product.GetProductResponseItem
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*


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

@JvmName("castFromRemoteToLocalGetProductResponseItem")
fun List<GetProductResponseItem>?.castFromRemoteToLocal(): List<SellerProductLocal> {
    val list = mutableListOf<SellerProductLocal>()
    this?.forEach {
        list.add(
            SellerProductLocal(
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

@JvmName("castFromLocalToRemoteSellerProductLocal")
fun List<SellerProductLocal>?.castFromLocalToRemote(): List<GetProductResponseItem> {
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

fun List<GetCategoryResponseItem>.toNameOnly(): String {
    val str = mutableListOf<String>()
    this.forEach {
        str.add(it.name.toString())
    }
    return str.joinToString()
}

@JvmName("toNameOnlyCategoriesItem")
fun List<CategoriesItem>.toNameOnly(): String {
    val str = mutableListOf<String>()
    this.forEach {
        str.add(it.name.toString())
    }
    return str.joinToString()
}

fun List<GetCategoryResponseItem>.toIntOnly(): String {
    val int = mutableListOf<Int>()
    this.forEach {
        it.id?.let { i ->
            int.add(i)
        }
    }
    return int.joinToString()
}

fun Any.convertRupiah(): String {
    val localId = Locale("in", "ID")
    val formatter = NumberFormat.getCurrencyInstance(localId)
    val strFormat = formatter.format(this)
    return strFormat
}

class MoneyTextWatcher(editText: EditText?) : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText>
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        val editText: EditText? = editTextWeakReference.get()
        if (editText == null || editText.text.toString() == "") {
            return
        }
        editText.removeTextChangedListener(this)
        val parsed: BigDecimal = parseCurrencyValue(editText.text.toString())
        val formatted: String = numberFormat.format(parsed)
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    companion object {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        fun parseCurrencyValue(value: String): BigDecimal {
            try {
                val replaceRegex = java.lang.String.format(
                    "[%s,.\\s]", Objects.requireNonNull(
                        numberFormat.currency
                    ).displayName
                )
                val currencyValue = value.replace(replaceRegex.toRegex(), "")
                return BigDecimal(currencyValue)
            } catch (e: Exception) {}
            return BigDecimal.ZERO
        }
    }

    init {
        editTextWeakReference = WeakReference(editText)
        numberFormat.maximumFractionDigits = 0
        numberFormat.roundingMode = RoundingMode.FLOOR
    }
}