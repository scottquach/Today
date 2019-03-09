package com.scottquach.today.room

import androidx.room.TypeConverter
import com.scottquach.today.HighlightStatus
import org.joda.time.DateTime
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Int?): Date? {
        return value?.let { Date(it.toLong()) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return DateTime().millis
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