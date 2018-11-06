package com.mytaxi.android_demo.utils

import com.mytaxi.android_demo.models.Driver
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

object TestData {

    const val validUsername = "crazydog335"
    const val validPassword = "venture"

    val driverSarah = Driver(
            "Sarah Scott",
            "(413) 868-2228",
            "imageUrl",
            "6834 charles st",
            toDate(LocalDate.parse("2002-10-18"))
    )

    val driverCamila = Driver(
            "Camila Hernandez",
            "(149) 278-5939",
            "imageUrl",
            "6044 avondale ave",
            toDate(LocalDate.parse("2011-05-29"))
    )

    private fun toDate(localDate: LocalDate) : Date {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }
}