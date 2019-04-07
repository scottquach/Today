package com.scottquach.today.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scottquach.today.model.HighlightStatus
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

@Entity(tableName = "highlights")
data class Highlight(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo var value: String,
    @ColumnInfo var status: HighlightStatus = HighlightStatus.INCOMPLETE,
    @ColumnInfo var created: Long = DateTime(DateTimeZone.UTC).millis,
    @ColumnInfo(name = "goal_id") var goalId: Int?
) {
    constructor(_value: String, _goalId: Int?): this(null, value = _value, goalId = _goalId)

    override fun toString(): String {
        return "id: $id value: $value status: $status created: $created goalId: $goalId"
    }
}