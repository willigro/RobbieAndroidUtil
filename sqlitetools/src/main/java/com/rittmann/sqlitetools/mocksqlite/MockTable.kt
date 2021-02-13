package com.rittmann.sqlitetools.mocksqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val CHANCE_TO_BE_NULL = .1
const val CHANCE_TO_BE_NEGATIVE = .5
const val DEFAULT_MAX_STRING = 50
const val DEFAULT_MAX_NUMBER = 1000
const val DEFAULT_CALENDAR_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val DEFAULT_CALENDAR_BETWEEN_YEAR = 30
const val TAG = "MockTable"

fun Table.mock(
    db: SQLiteDatabase,
    times: Int,
    resetTable: Boolean = false,
    closeDb: Boolean = true,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    callback: (() -> Unit)? = null
): JSONArray {

    val result = JSONArray()

    GlobalScope.launch {
        withContext(dispatcher) {
            if (resetTable) {
                db.delete(tbName, "", null)
                db.execSQL("DELETE FROM sqlite_sequence WHERE name='$tbName';")
            }

            for (i in 1..times) {
                val contentValues = ContentValues()

                columns.forEach {
                    val jo = JSONObject()

                    when (it.type) {
                        TableColumnType.TEXT -> {
                            val ruleType = getRuleType(tableRules, it.name)

                            val random = if (ruleType is TextColumnRule) {
                                randomString(it.isNotNull, getRule(tableRules, it))
                            } else {
                                randomStringCalendar(it.isNotNull, getRule(tableRules, it))
                            }

                            contentValues.put(it.name, random)
                        }

                        TableColumnType.INTEGER -> {
                            contentValues.put(
                                it.name, randomInteger(it.isNotNull, getRule(tableRules, it))
                            )
                        }

                        TableColumnType.REAL -> {
                            contentValues.put(
                                it.name, randomReal(it.isNotNull, getRule(tableRules, it))
                            )
                        }
                    }

                    result.put(jo)
                }

                contentValues.also {
                    db.insert(tbName, null, it)
                }
            }
            if (closeDb)
                db.close()
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }

    return result
}

fun Table.mockMain(
    db: SQLiteDatabase,
    times: Int,
    resetTable: Boolean = false,
    closeDb: Boolean = true
): JSONArray {

    val result = JSONArray()

    if (resetTable) {
        db.delete(tbName, "", null)
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='$tbName';")
    }

    for (i in 1..times) {
        val contentValues = ContentValues()
        val jo = JSONObject()

        columns.forEach {

            when (it.type) {
                TableColumnType.TEXT -> {
                    val ruleType = getRuleType(tableRules, it.name)

                    val random = if (ruleType is TextColumnRule) {
                        randomString(it.isNotNull, getRule(tableRules, it))
                    } else {
                        randomStringCalendar(it.isNotNull, getRule(tableRules, it))
                    }

                    contentValues.put(it.name, random)
                    jo.put(it.name, random)
                }

                TableColumnType.INTEGER -> {
                    val random = randomInteger(it.isNotNull, getRule(tableRules, it))
                    contentValues.put(it.name, random)
                    jo.put(it.name, random)
                }

                TableColumnType.REAL -> {
                    val random = randomReal(it.isNotNull, getRule(tableRules, it))
                    contentValues.put(it.name, random)
                    jo.put(it.name, random)
                }
            }
        }

        result.put(jo)
        contentValues.also {
            db.insert(tbName, null, it)
        }
    }

    if (closeDb)
        db.close()

    return result
}

fun getRuleType(tableRules: ArrayList<TableRules>, name: String): ColumnRule {
    for (tRule in tableRules) {
        if (tRule.columnName == name) {
            for (rule in tRule.columnRules) {
                return rule
            }
            break
        }
    }

    return TextColumnRule()
}

inline fun <reified T> getRule(tableRules: ArrayList<TableRules>, column: Column): T? {
    for (tRule in tableRules) {
        if (tRule.columnName == column.name) {
            for (rule in tRule.columnRules) {
                if (rule is T) {
                    return rule
                }
            }
            break
        }
    }

    return null
}

fun randomReal(isNotNull: Boolean, columnRule: RealColumnRule?): Double? {
    if (isNotNull.not() and canBeNull()) return null

    val rule: RealColumnRule =
        columnRule ?: RealColumnRule(0.0, DEFAULT_MAX_NUMBER.toDouble(), false)
    val r = Random()

    val res =
        rule.minNumber + (rule.maxNumber ?: DEFAULT_MAX_NUMBER - rule.minNumber) * r.nextDouble()

    if (rule.allowNegatives && Math.random() > CHANCE_TO_BE_NEGATIVE) return res * -1
    return res
}

fun randomInteger(isNotNull: Boolean, columnRule: IntegerColumnRule?): Int? {
    if (isNotNull.not() and canBeNull()) return null

    val rule: IntegerColumnRule = columnRule ?: IntegerColumnRule(0, DEFAULT_MAX_NUMBER, false)

    val res = Random(System.nanoTime()).nextInt(
        rule.maxNumber ?: DEFAULT_MAX_NUMBER - rule.minNumber + 1
    ) + rule.minNumber

    if (rule.allowNegatives && Math.random() > CHANCE_TO_BE_NEGATIVE) return res * -1
    return res
}

fun randomString(isNotNull: Boolean, columnRule: TextColumnRule?): String? {
    if (isNotNull.not() and canBeNull()) return null

    val generator = Random()
    val randomStringBuilder = StringBuilder()
    val randomLength: Int = generator.nextInt(columnRule?.maxLength ?: DEFAULT_MAX_STRING)
    var tempChar: Char
    for (i in 0 until randomLength) {
        tempChar = ((generator.nextInt(96) + 32).toChar())
        randomStringBuilder.append(tempChar)
    }
    return randomStringBuilder.toString()
}

fun randomStringCalendar(isNotNull: Boolean, columnRule: CalendarColumnRule?): String? {
    if (isNotNull.not() and canBeNull()) return null

    val rule: CalendarColumnRule = columnRule ?: CalendarColumnRule(DEFAULT_CALENDAR_FORMAT)

    // between
    val res: Long = if (rule.between != null) {
        val old = parseDate(rule.between.first)
        val new = parseDate(rule.between.second)

        (old.timeInMillis until new.timeInMillis).random()
    } else {
        val old = Calendar.getInstance().apply {
            add(Calendar.YEAR, -DEFAULT_CALENDAR_BETWEEN_YEAR)
        }
        val new = Calendar.getInstance().apply {
            add(Calendar.YEAR, DEFAULT_CALENDAR_BETWEEN_YEAR)
        }

        (old.timeInMillis until new.timeInMillis).random()
    }

    // by period


    val calendar = Calendar.getInstance().apply {
        timeInMillis = res
    }

    return dateFormat(calendar, rule.format).also {
        Log.i(TAG, it)
    }
}

fun canBeNull(): Boolean = Math.random() < CHANCE_TO_BE_NULL

@SuppressLint("SimpleDateFormat")
fun dateFormat(calendar: Calendar, format: String = DEFAULT_CALENDAR_FORMAT): String {
    val sFormat = SimpleDateFormat(format)
    sFormat.timeZone = calendar.timeZone
    return sFormat.format(calendar.time)
}

@SuppressLint("SimpleDateFormat")
fun parseDate(string: String, format: String = DEFAULT_CALENDAR_FORMAT): Calendar {
    val calendar = Calendar.getInstance()
    try {
        calendar.time = SimpleDateFormat(format).parse(string)!!
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return calendar
}