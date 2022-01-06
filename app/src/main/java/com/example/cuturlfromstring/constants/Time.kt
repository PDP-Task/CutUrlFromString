package com.example.cuturlfromstring.constants

import java.sql.Timestamp
import java.text.SimpleDateFormat

object Time {

    fun timeStamp(): String {
        val timestamp = Timestamp(System.currentTimeMillis())
        val format = SimpleDateFormat("HH:mm")
        val time = format.format(timestamp.time)

        return time.toString()
    }
}