package com.rittmann.sqlitetools.mocksqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

const val CHANCE_TO_BE_NULL = .1
const val CHANCE_TO_BE_NEGATIVE = .5
const val DEFAULT_MAX_STRING = 50
const val DEFAULT_MAX_NUMBER = 1000
const val TAG = "MockTable"

fun Table.mock(
    db: SQLiteDatabase,
    times: Int,
    resetTable: Boolean = false,
    closeDb: Boolean = true,
    callback: (() -> Unit)? = null
) {
    GlobalScope.launch {
        withContext(Dispatchers.IO) {
            if (resetTable) {
                db.delete(tbName, "", null)
                db.execSQL("DELETE FROM sqlite_sequence WHERE name='$tbName';")
            }

            for (i in 1..times) {
                val contentValues = ContentValues()

                columns.forEach {
                    when (it.type) {
                        TableColumnType.TEXT -> {
                            contentValues.put(
                                it.name, randomString(
                                    it.isNotNull, getRule(
                                        tableRules,
                                        it
                                    )
                                )
                            )
                        }
                        TableColumnType.INTEGER -> {
                            contentValues.put(
                                it.name, randomInteger(
                                    it.isNotNull, getRule(
                                        tableRules,
                                        it
                                    )
                                )
                            )
                        }
                        TableColumnType.REAL -> {
                            contentValues.put(
                                it.name, randomReal(
                                    it.isNotNull, getRule(
                                        tableRules,
                                        it
                                    )
                                )
                            )
                        }
                    }
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

    if (rule.allowNegatives && r.nextDouble() > CHANCE_TO_BE_NEGATIVE) return res
    return res * -1
}

fun randomInteger(isNotNull: Boolean, columnRule: IntegerColumnRule?): Int? {
    if (isNotNull.not() and canBeNull()) return null

    val res = (Random(System.nanoTime()).nextInt(
        (columnRule?.maxNumber ?: DEFAULT_MAX_NUMBER - (columnRule?.minNumber ?: 0) + 1).toInt()
    ) + (columnRule?.minNumber ?: 0))
    if (columnRule?.allowNegatives == true && Math.random() > CHANCE_TO_BE_NEGATIVE) return res
    return res * -1
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

fun canBeNull(): Boolean = Math.random() < CHANCE_TO_BE_NULL
