package id.co.binar.secondhand.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.co.binar.secondhand.data.local.model.BuyerProductLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface BuyerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProductHome(field: List<BuyerProductLocal>)

    @Query("SELECT * FROM tbl_home")
    fun getProductHome() : Flow<List<BuyerProductLocal>>

    @Query("DELETE FROM tbl_home")
    suspend fun removeProductHome()

    /*Query Search*/
}