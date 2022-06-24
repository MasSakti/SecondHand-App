package com.tegarpenemuan.secondhandecomerce.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "full_name") val full_name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone_number") val phone_number: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "image_url") val image_url: String,
    @ColumnInfo(name = "city") val city: String? = null

)
