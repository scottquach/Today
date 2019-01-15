package com.scottquach.today.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.scottquach.today.HighlightStatus
import java.util.*

@Entity(tableName = "highlights")
data class Highlight(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo var value: String,
    @ColumnInfo var status: HighlightStatus = HighlightStatus.INCOMPLETE,
    @ColumnInfo var created: Date = Date(),
    @ColumnInfo(name = "goal_id") var goalId: Int?
) {
    constructor(_value: String, _goalId: Int?): this(null, value = _value, goalId = _goalId)
}