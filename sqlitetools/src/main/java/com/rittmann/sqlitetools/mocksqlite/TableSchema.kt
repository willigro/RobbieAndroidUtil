package com.rittmann.sqlitetools.mocksqlite

import android.database.sqlite.SQLiteDatabase

object TableSchema {
    fun getDetails(
        db: SQLiteDatabase,
        table: String,
        excludeColumns: ArrayList<String>? = null
    ): Table {
        val cursor = db.rawQuery("PRAGMA table_info($table)", null)

        cursor.use {
            val nameIdx = cursor.getColumnIndexOrThrow("name")
            val typeIdx = cursor.getColumnIndexOrThrow("type")
            val notNullIdx = cursor.getColumnIndexOrThrow("notnull")
            val dfltValueIdx = cursor.getColumnIndexOrThrow("dflt_value")

            val columns = ArrayList<Column>()
            while (cursor.moveToNext()) {
                val name = cursor.getString(nameIdx)

                if (excludeColumns != null && excludeColumns.contains(name))
                    continue

                columns.add(
                    Column.build(
                        name,
                        cursor.getString(typeIdx),
                        cursor.getInt(notNullIdx),
                        cursor.getString(dfltValueIdx)
                    )
                )
            }
            return Table(table, columns)
        }
    }
}

data class Table(
    val tbName: String,
    val columns: ArrayList<Column>
) {
    var tableRules = arrayListOf<TableRule>()
}

data class Column(
    val name: String,
    val type: TableColumnType,
    val isNotNull: Boolean,
    val defaultValue: String?
) {
    companion object {
        fun build(name: String, type: String, isNotNull: Int, defaultValue: String?) =
            Column(
                name, type(type), isNotNull(isNotNull), defaultValue
            )
    }
}

enum class TableColumnType {
    INTEGER, TEXT, REAL
}

fun type(value: String) = when (value) {
    "INTEGER" -> TableColumnType.INTEGER
    "REAL" -> TableColumnType.REAL
    else -> TableColumnType.TEXT
}

fun isNotNull(value: Int) = value == 1