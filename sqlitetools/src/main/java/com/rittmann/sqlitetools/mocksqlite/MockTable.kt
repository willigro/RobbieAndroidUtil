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
const val DEFAULT_MAX_NUMBER = 1000.0
const val TAG = "MockTable"

fun Table.mock(
    db: SQLiteDatabase,
    times: Int,
    resetTable: Boolean = false,
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
            db.close()
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }
}

class MockRule(
    var maxLength: Int = DEFAULT_MAX_STRING,
    var maxValue: Double = DEFAULT_MAX_NUMBER,
    var minValue: Double = 0.0,
    var allowNegative: Boolean = false
)

fun getRule(tableRules: ArrayList<TableRule>, column: Column): MockRule {
    val mockRule = MockRule()

    for (tRule in tableRules) {
        if (tRule.columnName == column.name) {
            for (rule in tRule.rules) {
                when (rule.type) {
                    RuleType.MAX_LENGTH -> mockRule.maxLength = rule.value.toInt()
                    RuleType.MAX_NUMBER -> mockRule.maxValue = rule.value
                    RuleType.MIN_NUMBER -> mockRule.minValue = rule.value
                    RuleType.ALLOW_NEGATIVE -> mockRule.allowNegative = rule.isTo
                }
            }
            break
        }
    }

    return mockRule
}

fun randomReal(isNotNull: Boolean, mockRule: MockRule): Double? {
    if (isNotNull.not() and canBeNull()) return null

    val r = Random()
    val res = mockRule.minValue + (mockRule.maxValue - mockRule.minValue) * r.nextDouble()
    if (mockRule.allowNegative && r.nextDouble() > CHANCE_TO_BE_NEGATIVE) return res
    return res * -1
}

fun randomInteger(isNotNull: Boolean, mockRule: MockRule): Int? {
    if (isNotNull.not() and canBeNull()) return null

    val res =
        (Random(System.nanoTime()).nextInt((mockRule.maxValue - mockRule.minValue + 1).toInt()) + mockRule.minValue).toInt()
    if (mockRule.allowNegative && Math.random() > CHANCE_TO_BE_NEGATIVE) return res
    return res * -1
}

fun randomString(isNotNull: Boolean, mockRule: MockRule): String? {
    if (isNotNull.not() and canBeNull()) return null

    val generator = Random()
    val randomStringBuilder = StringBuilder()
    val randomLength: Int = generator.nextInt(mockRule.maxLength)
    var tempChar: Char
    for (i in 0 until randomLength) {
        tempChar = ((generator.nextInt(96) + 32).toChar())
        randomStringBuilder.append(tempChar)
    }
    return randomStringBuilder.toString()
}

fun canBeNull(): Boolean = Math.random() < CHANCE_TO_BE_NULL
