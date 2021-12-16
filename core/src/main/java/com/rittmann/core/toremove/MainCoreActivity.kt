package com.rittmann.core.toremove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.rittmann.core.R

class MainCoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_core)

        findViewById<LinearLayout>(R.id.linear)?.addView(TextView(this).apply { text = "One" })
        findViewById<ViewGroup>(R.id.linear)?.addView(TextView(this).apply { text = "Two" })
    }
}