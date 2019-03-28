package com.scottquach.today.home

import com.scottquach.today.room.Highlight

data class TodayModel(val status: Status, val highlight: Highlight?) {
    enum class Status {
        COMPLETE,
        NONE,
        PENDING
    }
}