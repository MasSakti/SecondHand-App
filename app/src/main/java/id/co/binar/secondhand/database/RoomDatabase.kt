package id.co.binar.secondhand.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.local.BuyerDao
import id.co.binar.secondhand.data.local.model.AuthLocal
import id.co.binar.secondhand.data.local.model.BuyerProductLocal
import id.co.binar.secondhand.data.local.model.BuyerCategoryLocal
import id.co.binar.secondhand.util.TypeConverter

@Database(entities = [
    AuthLocal::class,
    BuyerProductLocal::class,
    BuyerCategoryLocal::class
], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
    abstract fun buyerDao(): BuyerDao
}