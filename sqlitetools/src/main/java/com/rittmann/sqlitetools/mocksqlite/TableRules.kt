package com.rittmann.sqlitetools.mocksqlite


class TableRules(val columnName: String) {
    val columnRules = arrayListOf<ColumnRule>()

    fun addRule(columnRule: ColumnRule): TableRules {
        columnRules.add(columnRule)
        return this
    }
}

interface ColumnRule {

}

class TextColumnRule(
    val maxLength: Int? = null
) : ColumnRule

class RealColumnRule(
    val minNumber: Double = 0.0,
    val maxNumber: Double? = null,
    val allowNegatives: Boolean = false
) : ColumnRule

class IntegerColumnRule(
    val minNumber: Int = 0,
    val maxNumber: Int? = null,
    val allowNegatives: Boolean = false
) : ColumnRule

class CalendarColumnRule(
    val format: String = "",
    val between: Pair<String, String>? = null,
    val registersByPeriod: Pair<CalendarColumnRulePeriod, Int>? = null
) : ColumnRule {
    enum class CalendarColumnRulePeriod {
        DAY, WEEK, MONTH, HOUR
    }
}