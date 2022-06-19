package id.co.binar.secondhand.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import id.co.binar.secondhand.data.local.model.AuthLocal

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAccount(field: AuthLocal)

    @Query("SELECT * FROM tbl_auth WHERE token=:token AND id=:id")
    fun getAccount(token: String, id: Int) : LiveData<AuthLocal>

    @Delete
    suspend fun removeAccount(field: AuthLocal)

    @Query("DELETE FROM tbl_auth")
    suspend fun logout()
}