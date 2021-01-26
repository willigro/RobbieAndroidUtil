package com.rittmann.robbie

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rittmann.androidtools.log.log
import com.rittmann.androidtools.start
import com.rittmann.baselifecycle.base.BaseActivity
import com.rittmann.robbie.sqlite.HelperDAO
import com.rittmann.sqlitetools.mocksqlite.ExportDatabase
import com.rittmann.sqlitetools.mocksqlite.IntegerColumnRule
import com.rittmann.sqlitetools.mocksqlite.RealColumnRule
import com.rittmann.sqlitetools.mocksqlite.TableRules
import com.rittmann.sqlitetools.mocksqlite.TableSchema
import com.rittmann.sqlitetools.mocksqlite.TextColumnRule
import com.rittmann.sqlitetools.mocksqlite.mock
import kotlinx.android.synthetic.main.activity_main.execute_sql
import kotlinx.android.synthetic.main.activity_main.show_dialogs
import kotlinx.android.synthetic.main.activity_main.show_progress

class MainActivity : BaseActivity() {

    override var resIdViewReference: Int = R.id.layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show_progress.setOnClickListener {
            start<ProgressActivity>()
        }

        show_dialogs.setOnClickListener {
            start<DialogsActivity>()
        }

        execute_sql.setOnClickListener {
            showProgress()
            checkWriteStoragePermissions()
        }
    }

    private fun tableOne() {
        val dao = HelperDAO(this)
        TableSchema.getDetails(
            dao.readableDatabase,
            table = "tb_test",
            excludeColumns = arrayListOf("tb_id")
        ).also {
            it.tableRules.add(
                TableRules("tb_text_not_null").addRule(TextColumnRule(50))
            )

            it.tableRules.add(
                TableRules("tb_integer_not_null").addRule(IntegerColumnRule(10, 30, true))
            )

            it.tableRules.add(
                TableRules("tb_real_not_null").addRule(RealColumnRule(.2, 3.0, true))
            )

            it.toString().log(beautiful = false)
            it.mock(dao.writableDatabase, times = 5, resetTable = true) {
                ExportDatabase("Main").export(HelperDAO(this).writableDatabase) { export ->
                    export.resultSet.toString().log()
                    hideProgress()
                }
            }
        }
    }

    private fun checkWriteStoragePermissions() {
        val permissionsList = ArrayList<String>()

        addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionsList.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsList.toTypedArray(),
                1
            )
        } else
            tableOne()
    }

    private fun addPermission(permissionsList: MutableList<String>, permission: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsList.add(permission)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    tableOne()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}