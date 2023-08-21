package com.truesparrow.sales.util

import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}


@RequiresApi(Build.VERSION_CODES.O)
fun parseTime(date: String): Instant? {
    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val zonedDateTime = ZonedDateTime.parse(date, formatter)
        zonedDateTime.toInstant()
    } catch (e: Exception) {
        null
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(time: Instant): String {
    val zoneId = ZoneId.systemDefault()
    val zonedDateTime = ZonedDateTime.ofInstant(time, zoneId)
    val formatter = DateTimeFormatter.ofPattern("EEEE, h:mma")
    return formatter.format(zonedDateTime).replace("AM", "am").replace("PM", "pm")
}