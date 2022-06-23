package com.tegarpenemuan.secondhandecomerce.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tegarpenemuan.secondhandecomerce.data.local.UserDAO
import com.tegarpenemuan.secondhandecomerce.data.local.UserEntity

/**
 * com.tegarpenemuan.secondhandecomerce.database
 *
 * Created by Tegar Penemuan on 24/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

@Database(entities = [UserEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO

    companion object {
        private const val DB_NAME = "MyDatabase.db"

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