package com.xuaum.cstv

import com.xuaum.cstv.util.MyDateFormatter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateFormatterTest {
    @Test
    fun dateFormatter_formatTodayString_isCorrect() {
        val now = LocalDateTime.now(ZoneId.of("UTC"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))

        assertEquals("Hoje", MyDateFormatter().stringToAppDateString(now).take(4))
    }

    @Test
    fun dateFormatter_formatWeekString_isCorrect() {
        val weekDays = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
        val thisWeek = List(6) {
            LocalDateTime.now(ZoneId.of("UTC")).plusDays((it + 1).toLong())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        }.map { MyDateFormatter().stringToAppDateString(it).take(3) }

        print(thisWeek)

        assertTrue(
            weekDays.containsAll(thisWeek)
        )
    }

    @Test
    fun dateFormatter_formatYearString_isCorrect() {
        val future = LocalDateTime.of(2030, 4, 27, 12, 0)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))

        assertEquals("27/04", MyDateFormatter().stringToAppDateString(future).take(5))
    }
}