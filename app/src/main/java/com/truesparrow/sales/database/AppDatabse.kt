package com.truesparrow.sales.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.truesparrow.sales.dao.AuthorizationDao
import com.truesparrow.sales.entity.AuthorizationEntity


@Database(entities = [AuthorizationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authorizationDao(): AuthorizationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "salessparrow_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

