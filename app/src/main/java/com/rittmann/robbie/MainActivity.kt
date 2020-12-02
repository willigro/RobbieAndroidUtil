package com.rittmann.robbie

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.widgets.dialog.DialogUtil
import kotlinx.android.synthetic.main.activity_main.show_dialogs
import kotlinx.android.synthetic.main.activity_main.show_progress

class MainActivity : AppCompatActivity() {

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
    }

    fun show(){
        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show()
    }
}

inline fun <reified A : Activity> Context.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}
