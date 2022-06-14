package binar.and3.kelompok1.secondhand.data.local

import androidx.room.*

@Dao
interface UserDAO {
    @Query("Select * From user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("UPDATE user SET full_name = :fullName, address = :address, phone_number = :phoneNumber WHERE id = :id")
    suspend fun updateUser(id: Int, fullName: String, address: String, phoneNumber: String)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity): Int
}