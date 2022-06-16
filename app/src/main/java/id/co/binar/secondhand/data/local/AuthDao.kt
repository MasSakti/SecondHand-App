package id.co.binar.secondhand.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.co.binar.secondhand.data.local.model.AuthLocal

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(field: AuthLocal)

    @Query("UPDATE tbl_auth SET token=:token WHERE full_name=:fullName AND email=:email")
    suspend fun login(fullName: String, email: String, token: String)

    @Query("DELETE FROM tbl_auth")
    suspend fun logout()
}