package com.mertyazi.footballapp.shared.presentation

import com.mertyazi.footballapp.Constants

interface DateFormatter {
    fun format(dateString: String?, status: String?): String
}

object DateFormatterForTR: DateFormatter {

    override fun format(dateString: String?, status: String?): String {
        if (dateString != null) {
            val date = dateString.split("T")[0]
            val timeUTC =
                dateString.substring(
                    dateString.lastIndexOf('T') + 1, dateString.lastIndexOf(":00Z"))
            val time = timeUTC.split(":")
            val timeHour: String = when (time[0]) {
                "21" -> "00"
                "22" -> "1"
                "23" -> "2"
                "00" -> "3"
                else -> (time[0].toInt() + 3).toString()
            }
            if (status == Constants.TODAY) {
                return timeHour + ":" + time[1]
            } else {
                return date + " | " +  timeHour + ":" + time[1]
            }
        } else {
            return ""
        }
    }

}