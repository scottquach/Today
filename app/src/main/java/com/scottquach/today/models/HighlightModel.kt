package com.scottquach.today.models

import com.scottquach.today.HighlightStatus
import java.util.*

data class Highlight(val value: String, val goal: String, var status: HighlightStatus = HighlightStatus.INCOMPLETE, val created: Date = Date())