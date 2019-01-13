package com.scottquach.today.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Highlight::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun highlightDao(): HighlightDao
}