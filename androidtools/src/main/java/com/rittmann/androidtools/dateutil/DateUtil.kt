package com.rittmann.androidtools.dateutil

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

interface DateUtil {
    fun setPattern(format: String)
    fun today(): Calendar
}

object DateUtilImpl : DateUtil {

    const val SIMPLE_DATE_FORMAT = "dd/MM/yyyy"
    const val SIMPLE_HOUR_FORMAT = "HH:mm:ss"
    const val DATE_HOUR_FORMAT = "yyyy-MM-dd HH:mm:ss"

    private var patterFormat = DATE_HOUR_FORMAT

    override fun setPattern(format: String) {
        patterFormat = format
    }

    override fun today(): Calendar {
        return Calendar.getInstance()
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(calendar: Calendar, format: String = patterFormat): String {
        val sFormat = SimpleDateFormat(format)
        sFormat.timeZone = calendar.timeZone
        return sFormat.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDate(string: String, format: String = patterFormat): Calendar {
        val calendar = today()
        try {
            calendar.time = SimpleDateFormat(format).parse(string)!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return calendar
    }

    fun parseDate(day: Int, month: Int, year: Int): Calendar {
        val calendar = today()

        try {
            calendar.set(year, month - 1, day)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return calendar
    }
}