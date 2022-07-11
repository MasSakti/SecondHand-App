package binar.and3.kelompok1.secondhand.data.local.seller

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "product_detail")
data class SellerEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "base_price") val basePrice: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "image_name") val imageName: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "categories") val categories: List<Categories>
) {
    data class Categories(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String
    )
}