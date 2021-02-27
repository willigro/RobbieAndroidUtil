package com.rittmann.robbie.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDAO(context: Context) : SQLiteOpenHelper(context, "testdatabase", null, 2) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.apply {
            createTables(this)
        }
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        sqLiteDatabase?.execSQL(createTableTwo())
    }

    private fun createTables(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(create())
    }
}

fun create() = "CREATE TABLE IF NOT EXISTS tb_test (" +
        "tb_id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "tb_integer INTEGER NULL," +
        "tb_integer_not_null INTEGER NOT NULL," +
        "tb_integer_not_null_with_default INTEGER NOT NULL DEFAULT 1," +
        "tb_text_null TEXT NULL," +
        "tb_text_not_null TEXT NOT NULL," +
        "tb_text_not_null_with_default TEXT NOT NULL DEFAULT 'One value'," +
        "tb_real REAL NULL," +
        "tb_real_not_null REAL NOT NULL," +
        "tb_real_not_null_default REAL NOT NULL DEFAULT 1.0," +
        "tb_date TEXT NULL);"

fun createTableTwo() = "CREATE TABLE IF NOT EXISTS tb_test_two (" +
        "tb_id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "tb_integer INTEGER NULL," +
        "tb_integer_not_null INTEGER NOT NULL," +
        "tb_integer_not_null_with_default INTEGER NOT NULL DEFAULT 1," +
        "tb_text_null TEXT NULL," +
        "tb_text_not_null TEXT NOT NULL," +
        "tb_text_not_null_with_default TEXT NOT NULL DEFAULT 'One value'," +
        "tb_real REAL NULL," +
        "tb_real_not_null REAL NOT NULL," +
        "tb_real_not_null_default REAL NOT NULL DEFAULT 1.0," +
        "tb_date TEXT NULL);"