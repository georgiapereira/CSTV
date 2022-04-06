package com.xuaum.cstv.util

import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*

class MyDateFormatter {


    fun stringToAppDateString(date: String): String {
        val serverFormat = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )

        val dateAsLocal =
            LocalDateTime.parse(date, serverFormat).toLocalTimeZone("UTC")

        val currentDate = LocalDateTime.now().asLocalTimeZone

        return buildString {

            when {
                dateAsLocal.dayOfYear == currentDate.dayOfYear -> {
                    append("Hoje")
                }
                Period.between(currentDate.toLocalDate(), dateAsLocal.toLocalDate())
                    .get(ChronoUnit.DAYS) < 7 -> {
                    val weekDay = dateAsLocal.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale("pt", "BR")
                    ).replaceFirstChar { it.uppercase() }

                    append(weekDay)
                }
                else -> {
                    val format = DateTimeFormatter.ofPattern(
                        "dd/MM"
                    )
                    val dayOfYear = dateAsLocal.toLocalDate()
                        .format(format)
                    append(dayOfYear)
                }
            }

            append(", ")
            append(
                dateAsLocal.toLocalTime()
                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
            )
        }
    }

}