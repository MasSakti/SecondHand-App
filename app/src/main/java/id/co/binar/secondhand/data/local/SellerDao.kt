package id.co.binar.secondhand.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.co.binar.secondhand.data.local.model.SellerCategoryLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface SellerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCategoryHome(field: List<SellerCategoryLocal>)

    @Query("SELECT * FROM tbl_category")
    fun getCategoryHome() : Flow<List<SellerCategoryLocal>>

    @Query("DELETE FROM tbl_category")
    suspend fun removeCategoryHome()
}