package com.tegarpenemuan.secondhandecomerce.data.local

import androidx.room.*

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Delete
    suspend fun deleteUser(userEntity: UserEntity): Int

    @Query("UPDATE user SET full_name = :full_name, phone_number = :phone_number,address = :address,image_url = :image_url,city = :city WHERE id = :id")
    suspend fun updateUser(
        id: String,
        full_name: String,
        phone_number: String,
        address: String,
        image_url: String,
        city: String
    )
}