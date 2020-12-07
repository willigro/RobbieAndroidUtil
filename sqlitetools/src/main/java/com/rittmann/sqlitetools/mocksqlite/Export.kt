package com.rittmann.sqlitetools.mocksqlite

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class ExportDatabase {
    fun export(
        db: SQLiteDatabase,
        closeDb: Boolean = true,
        callback: (() -> Unit)? = null
    ) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val resultSet = JSONArray()

                TableSchema.getAllTables(db).forEach {
                    val searchQuery = "SELECT  * FROM $it"
                    val cursor: Cursor = db.rawQuery(searchQuery, null)

                    while (cursor.moveToNext()) {
                        val totalColumn: Int = cursor.columnCount
                        val rowObject = JSONObject()
                        rowObject.put("table_name", it)
                        for (i in 0 until totalColumn) {
                            if (cursor.getColumnName(i) != null) {
                                try {
                                    if (cursor.getString(i) != null) {
                                        rowObject.put(cursor.getColumnName(i), cursor.getString(i))
                                    } else {
                                        rowObject.put(cursor.getColumnName(i), "")
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        resultSet.put(rowObject)
                        cursor.moveToNext()
                    }

                    cursor.close()
                }

                if (closeDb)
                    db.close()

                callback?.invoke()
            }
        }
    }
}