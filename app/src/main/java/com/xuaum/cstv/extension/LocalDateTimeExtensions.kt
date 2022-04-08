package com.xuaum.cstv.extension

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object LocalDateTimeExtensions {
    fun LocalDateTime.toLocalTimeZone(from: String): ZonedDateTime =
        this.atZone(ZoneId.of(from)).withZoneSameInstant(
            ZoneId.of(TimeZone.getDefault().id)
        )

    val LocalDateTime.asLocalTimeZone: ZonedDateTime
        get() = this.atZone(ZoneId.of(TimeZone.getDefault().id))
}