package com.scottquach.today.room

import androidx.room.TypeConverter
import com.scottquach.today.HighlightStatus
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun statusToString(value: HighlightStatus?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun stringToStatus(value: String?): HighlightStatus? {
        return value?.let { HighlightStatus.valueOf(value) }
    }
}