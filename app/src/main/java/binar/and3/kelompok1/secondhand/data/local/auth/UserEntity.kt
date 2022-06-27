package binar.and3.kelompok1.secondhand.data.local.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey var id: Int?,
    @ColumnInfo(name = "full_name") var fullName: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "phone_number") var phoneNumber: String? = null,
    @ColumnInfo(name = "city") var city: String? = null,
    @ColumnInfo(name = "address") var address: String? = null,
    @ColumnInfo(name = "imageUrl") var imageUrl: String? = null
)
