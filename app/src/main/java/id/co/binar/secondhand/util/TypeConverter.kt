package id.co.binar.secondhand.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.co.binar.secondhand.data.local.model.CategoriesItemLocal

class TypeConverter {
    @TypeConverter
    fun fromListToString(list: List<CategoriesItemLocal>?) : String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToList(string: String?) : List<CategoriesItemLocal>? {
        val list = object : TypeToken<List<CategoriesItemLocal>?>() {}.type
        return Gson().fromJson(string, list)
    }
}