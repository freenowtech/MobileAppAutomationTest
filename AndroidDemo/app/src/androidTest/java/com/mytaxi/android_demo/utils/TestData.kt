package com.mytaxi.android_demo.utils

import com.mytaxi.android_demo.models.Driver
import com.mytaxi.android_demo.utils.DateUtils.toDate
import java.time.LocalDate

object TestData {

    const val VALID_USERNAME = "crazydog335"
    const val VALID_PASSWORD = "venture"

    object Drivers {

        val Sarah = Driver(
                "Sarah Scott",
                "(413) 868-2228",
                "imageUrl",
                "6834 charles st",
                DateUtils.toDate(LocalDate.parse("2002-10-18"))
        )

        val Camila = Driver(
                "Camila Hernandez",
                "(149) 278-5939",
                "imageUrl",
                "6044 avondale ave",
                toDate(LocalDate.parse("2011-05-29"))
        )
    }
}