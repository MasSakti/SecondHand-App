package binar.and3.kelompok1.secondhand.data.local.buyer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buyer_product")
data class BuyerEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "base_price") val base_price: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "image_name") val imageName: String,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "user_id") var userId: Int,
)
