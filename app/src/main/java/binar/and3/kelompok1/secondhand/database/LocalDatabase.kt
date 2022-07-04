package binar.and3.kelompok1.secondhand.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import binar.and3.kelompok1.secondhand.data.local.auth.UserDAO
import binar.and3.kelompok1.secondhand.data.local.auth.UserEntity
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerDAO
import binar.and3.kelompok1.secondhand.data.local.buyer.BuyerEntity

@Database(entities = [UserEntity::class, BuyerEntity::class], version = 4)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun buyerDAO(): BuyerDAO

    companion object {
        private const val DB_NAME = "SecondHand.db"

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}