package com.scottquach.today.model

import com.scottquach.today.room.Highlight

data class TodayModel(val status: Status, val highlight: Highlight?) {
    enum class Status {
        COMPLETE,
        NONE,
        PENDING
    }
}