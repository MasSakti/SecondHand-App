package binar.and3.kelompok1.secondhand.data.local.auth

import androidx.room.*

@Dao
interface UserDAO {
    @Query("Select * From user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("UPDATE user SET full_name = :fullName, email = :email, password = :password, phone_number = :phoneNumber, address = :address, imageUrl = :imageUrl WHERE id = :id")
    suspend fun updateUser(id: Int, fullName: String, email: String, password: String, phoneNumber: String, address: String, imageUrl: String )

    @Delete
    suspend fun removeUser(userEntity: UserEntity): Int
}