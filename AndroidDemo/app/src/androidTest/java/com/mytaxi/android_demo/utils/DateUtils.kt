package com.mytaxi.android_demo.utils

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object DateUtils {

    fun toDate(localDate: LocalDate) : Date {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    fun toLocalDate(date: Date) : LocalDate {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}