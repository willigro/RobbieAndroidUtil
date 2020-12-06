package com.rittmann.sqlitetools.mocksqlite

data class TableRule(
    val columnName: String
) {
    val rules = arrayListOf<Rule>()

    fun addRule(type: RuleType, value: Int): TableRule {
        rules.add(Rule(type, value = value.toDouble()))
        return this
    }

    fun addRule(type: RuleType, value: Double): TableRule {
        rules.add(Rule(type, value = value))
        return this
    }

    fun addRule(type: RuleType, value: Boolean): TableRule {
        rules.add(Rule(type, isTo = value))
        return this
    }
}

data class Rule(
    val type: RuleType,
    val value: Double = 0.0,
    val isTo: Boolean = false
)

enum class RuleType {
    MAX_LENGTH,
    MIN_NUMBER,
    MAX_NUMBER,
    ALLOW_NEGATIVE
}