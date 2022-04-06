package com.xuaum.cstv.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

fun LocalDateTime.toLocalTimeZone(from: String): ZonedDateTime = this.atZone(ZoneId.of(from)).withZoneSameInstant(
        ZoneId.of(TimeZone.getDefault().id)
    )

val LocalDateTime.asLocalTimeZone: ZonedDateTime
    get() = this.atZone(ZoneId.of(TimeZone.getDefault().id))