package com.xuaum.cstv.util

import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor
import java.util.*

class MyDateFormatter {


    fun stringToAppDateString(date: String): String {
        val serverFormat = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )

        val dateAsLocal =
            LocalDateTime.parse(date, serverFormat).atZone(ZoneId.of("UTC")).withZoneSameInstant(
                ZoneId.of(TimeZone.getDefault().id)
            )

        val currentDate = LocalDateTime.now().atZone(ZoneId.of(TimeZone.getDefault().id))

        var appDateString = ""
        appDateString += when {
            dateAsLocal.dayOfYear == currentDate.dayOfYear -> {
                "Hoje"
            }
            Period.between(currentDate.toLocalDate(), dateAsLocal.toLocalDate())
                .get(ChronoUnit.DAYS) < 7 -> {
                dateAsLocal.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale("pt", "BR")
                ).replaceFirstChar { it.uppercase() }
            }
            else -> {
                val format = DateTimeFormatter.ofPattern(
                    "dd/MM"
                )
                dateAsLocal.toLocalDate()
                    .format(format)
            }
        }

        appDateString += ", "
        appDateString += dateAsLocal.toLocalTime()
            .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

        return appDateString
    }

}