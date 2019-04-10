package com.scottquach.today.room

import androidx.room.TypeConverter
import com.scottquach.today.model.HighlightStatus
import org.joda.time.DateTime
import java.util.*

class Converters {
    @TypeConverter
    fun toDateTime(value: String): DateTime {
        return DateTime(value)
    }

    @TypeConverter
    fun fromDateTime(date: DateTime): String {
        return date.toString()
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