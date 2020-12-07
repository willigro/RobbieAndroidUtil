package com.rittmann.sqlitetools.mocksqlite

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ExportDatabase(folder: String) {

    private val rootPath: String =
        android.os.Environment.getExternalStorageDirectory().toString() + "/$folder/Export/"

    private var resultSet = JSONArray()

    fun toFile() {
        val locale = Locale("pt", "BR")
        val dateFormat = SimpleDateFormat("dd_MM_yyyy", locale)
        val date: Calendar = Calendar.getInstance()
        val hourFormat = SimpleDateFormat("HH_mm_ss", locale)
        val hour: Calendar = Calendar.getInstance()
        val nameFile = "export_${dateFormat.format(date.time)}_${hourFormat.format(hour.time)}.txt"
        try {
            val root = File(rootPath)
            if (!root.exists()) root.mkdirs()
            val gpxfile = File(root, nameFile)
            val writer = FileWriter(gpxfile)
            writer.append(resultSet.toString())
            writer.flush()
            writer.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun export(
        db: SQLiteDatabase,
        closeDb: Boolean = true,
        callback: ((ExportDatabase) -> Unit)? = null
    ) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                resultSet = JSONArray()

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

                callback?.invoke(this@ExportDatabase)
            }
        }
    }
}