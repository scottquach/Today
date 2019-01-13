package com.scottquach.today.models

import java.util.*

data class Goal(val name: String, val created: Date = Date(), var completedTimes: Array<Date>)