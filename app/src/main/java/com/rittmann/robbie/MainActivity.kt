package com.rittmann.robbie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.rittmann.androidtools.log.log
import com.rittmann.baselifecycle.base.BaseActivity
import com.rittmann.robbie.sqlite.HelperDAO
import com.rittmann.sqlitetools.mocksqlite.RuleType
import com.rittmann.sqlitetools.mocksqlite.TableRule
import com.rittmann.sqlitetools.mocksqlite.TableSchema
import com.rittmann.sqlitetools.mocksqlite.mock
import com.rittmann.widgets.dialog.DialogUtil
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
            findViewById<View>(R.id.layout).post {
                DialogUtil().init(this, "Testing ok show", "Title", ok = true, show = true)

                DialogUtil().init(
                    this,
                    "Testing ok cancelable false",
                    "Title",
                    ok = true,
                    show = true,
                    cancelable = false
                )

                DialogUtil().init(
                    this,
                    "Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger textTesting ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger textTesting ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text",
                    "Title",
                    ok = false,
                    show = true,
                    cancelable = false
                )
            }
        }

        execute_sql.setOnClickListener {
            showProgress()
            val dao = HelperDAO(this)
            TableSchema.getDetails(
                dao.readableDatabase,
                table = "tb_test",
                excludeColumns = arrayListOf("tb_id")
            ).also {
                it.tableRules.add(
                    TableRule("tb_text_not_null")
                        .addRule(RuleType.MAX_LENGTH, 10)
                )

                it.tableRules.add(
                    TableRule("tb_integer_not_null")
                        .addRule(RuleType.MIN_NUMBER, 10)
                        .addRule(RuleType.MAX_NUMBER, 30)
                        .addRule(RuleType.ALLOW_NEGATIVE, true)
                )

                it.tableRules.add(
                    TableRule("tb_real_not_null")
                        .addRule(RuleType.MIN_NUMBER, .2)
                        .addRule(RuleType.MAX_NUMBER, 3.0)
                        .addRule(RuleType.ALLOW_NEGATIVE, true)
                )

                it.toString().log(beautiful = false)
                it.mock(dao.writableDatabase, times = 5, resetTable = true) {
                    hideProgress()
                }
            }
        }
    }

    fun show() {
        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show()
    }
}

inline fun <reified A : Activity> Context.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}
