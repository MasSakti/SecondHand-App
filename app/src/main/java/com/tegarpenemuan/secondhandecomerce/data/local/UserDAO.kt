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
}