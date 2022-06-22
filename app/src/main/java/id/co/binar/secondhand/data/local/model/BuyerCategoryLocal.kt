package id.co.binar.secondhand.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_category_home")
@Parcelize
data class BuyerCategoryLocal(

    @ColumnInfo(name = "createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = null,

    var check: Boolean? = false
) : Parcelable