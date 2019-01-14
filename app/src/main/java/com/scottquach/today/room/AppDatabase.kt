package com.scottquach.today.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Highlight::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun highlightDao(): HighlightDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase( context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
            }
            return INSTANCE as AppDatabase
        }
    }
}