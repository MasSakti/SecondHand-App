package id.co.binar.secondhand.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_home")
@Parcelize
data class BuyerProductLocal(

    @ColumnInfo(name = "image_name")
    val imageName: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,

    @ColumnInfo(name = "user_id")
    val userId: Int? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "base_price")
    val basePrice: Int? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "categories")
    val categories: List<CategoriesItemLocal>? = null
) : Parcelable

@Parcelize
data class CategoriesItemLocal(
    val name: String? = null,
    val id: Int? = null
) : Parcelable