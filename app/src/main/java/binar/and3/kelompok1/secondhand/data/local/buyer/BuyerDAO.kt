package binar.and3.kelompok1.secondhand.data.local.buyer

import androidx.room.*

@Dao
interface BuyerDAO {
    @Query("Select * From buyer_product")
    suspend fun getProduct(): BuyerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(buyerEntity: BuyerEntity): Long

    @Delete
    suspend fun removeProduct(buyerEntity: BuyerEntity): Int
}