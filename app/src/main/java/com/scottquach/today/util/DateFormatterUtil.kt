package com.scottquach.today.util

import org.joda.time.DateTime

object DateFormatterUtil {

    /**
     * Returns a standardized human readable representation of a date that includes the year
     */
    fun getYearHumanFriendly(dateTime: DateTime): String {
        return "${dateTime.toString("MMMM")} ${dateTime.dayOfMonth}${getLastDigitSufix(
            dateTime.dayOfMonth
        )} ${dateTime.year}"
    }

    /**
     * Returns a standardized human readable representation of a date that includes the day of the week (no year)
     */
    fun getDayOfWeekHumanFriendly(dateTime: DateTime): String {
        return "${dateTime.toString("EEEE")}, ${dateTime.toString("MMM")} ${dateTime.dayOfMonth}"
    }

    fun getTimeHumanFriendly(time: Long): String {
        val dateTime = DateTime(time)
        return "${dateTime.toString("hh")}:${dateTime.toString("mm")} ${dateTime.toString("aa")}"
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