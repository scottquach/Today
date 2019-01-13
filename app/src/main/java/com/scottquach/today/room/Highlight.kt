package com.scottquach.today.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scottquach.today.HighlightStatus
import java.util.*

@Entity
data class Highlight(
    @PrimaryKey var id: Int,
    @ColumnInfo var value: String,
    @ColumnInfo var status: HighlightStatus,
    @ColumnInfo var created: Date,
    @ColumnInfo var goalId: Int
)