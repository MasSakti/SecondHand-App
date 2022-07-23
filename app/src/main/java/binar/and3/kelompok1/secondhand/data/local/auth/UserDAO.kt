package binar.and3.kelompok1.secondhand.data.local.auth

import androidx.room.*

@Dao
interface UserDAO {
    @Query("Select * From user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Delete
    suspend fun removeUser(userEntity: UserEntity): Int
}