package com.scottquach.today

import org.joda.time.DateTime

object DateFormatterUtil {

    /**
     * Returns a standardized human readable representation of a date
     */
    fun getHumanFriendly(time: Long): String {
        val dateTime = DateTime(time)
        return "${dateTime.toString("MMMM")} ${dateTime.dayOfMonth}${getLastDigitSufix(dateTime.dayOfMonth)} ${dateTime.year}"
    }

    /**
     * Returns the correct suffix for the last digit (1st, 2nd, .. , 13th, .. , 23rd)
     */
    private fun getLastDigitSufix(number: Int): String {
        return when (if (number < 20) number else number % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}