package com.truesparrow.sales.util

import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.absoluteValue

@ColorInt
fun String.toHslColor(saturation: Float = 0.5f, lightness: Float = 0.4f): Int {
    val hue = fold(0) { acc, char -> char.code + acc * 37 } % 360
    return ColorUtils.HSLToColor(floatArrayOf(hue.absoluteValue.toFloat(), saturation, lightness))
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertTime(inputTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    val instant = Instant.from(formatter.parse(inputTime))

    val systemZone = ZoneId.systemDefault()
    val localDateTime = LocalDateTime.ofInstant(instant, systemZone)

    val outputFormatter = DateTimeFormatter.ofPattern("h a")
    return outputFormatter.format(localDateTime)
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

fun convertDateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}

fun convertToISO8601(startDate: String, startTime: String): String {
    // Parse the date and time strings
    val datePattern = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timePattern = SimpleDateFormat("HH:mm", Locale.getDefault())

    val parsedDate = datePattern.parse(startDate)
    val parsedTime = timePattern.parse(startTime)

    // Combine date and time
    val combinedDateTime = Calendar.getInstance()
    combinedDateTime.time = parsedDate
    combinedDateTime.set(Calendar.HOUR_OF_DAY, parsedTime.hours)
    combinedDateTime.set(Calendar.MINUTE, parsedTime.minutes)
    combinedDateTime.set(Calendar.SECOND, 0)

    // Set the timezone to UTC
    combinedDateTime.timeZone = TimeZone.getTimeZone("UTC")

    // Format as ISO 8601 without 'Z' at the end
    val iso8601Pattern = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    iso8601Pattern.timeZone = TimeZone.getTimeZone("UTC")

    // Format the output to remove ':' in the timezone offset
    val iso8601DateTime = iso8601Pattern.format(combinedDateTime.time)
    return iso8601DateTime.substring(0, iso8601DateTime.length - 2)  +iso8601DateTime.substring(iso8601DateTime.length - 2)
}


fun extractDateAndTime(input: String): Pair<String, String>? {
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        val date = dateFormat.parse(input)

        val datePattern = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timePattern = SimpleDateFormat("HH:mm", Locale.getDefault())

        val formattedDate = datePattern.format(date)
        val formattedTime = timePattern.format(date)

        return Pair(formattedDate, formattedTime)
    } catch (e: ParseException) {
        e.printStackTrace()
        return null
    }
}