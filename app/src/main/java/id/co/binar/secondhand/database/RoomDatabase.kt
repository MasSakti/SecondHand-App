package id.co.binar.secondhand.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.co.binar.secondhand.data.local.AuthDao
import id.co.binar.secondhand.data.local.model.AuthLocal

@Database(entities = [
    AuthLocal::class
], version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
}