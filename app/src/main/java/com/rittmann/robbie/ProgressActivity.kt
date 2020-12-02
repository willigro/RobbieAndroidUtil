package com.rittmann.robbie

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.widgets.progress.ProgressVisibleControl
import kotlinx.android.synthetic.main.activity_progress.custom_view_progress
import kotlinx.android.synthetic.main.activity_progress.normal_progress
import kotlinx.android.synthetic.main.activity_progress.reset_view_progress
import kotlinx.android.synthetic.main.custom_progress.view.cancel

class ProgressActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        ProgressVisibleControl.init(this)

        normal_progress.setOnClickListener {
            ProgressVisibleControl.show(true) {
                Toast.makeText(this, "Normal progress dismiss callback", Toast.LENGTH_SHORT).show()
            }
        }

        custom_view_progress.setOnClickListener {
            ProgressVisibleControl.init(this)
                .customLayout(R.layout.custom_progress) {
                    it.cancel.setOnClickListener {
                        ProgressVisibleControl.hide()

                        ProgressVisibleControl.show(true) {
                            Toast.makeText(
                                this,
                                "Custom progress dismiss callback 2",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }.show(true) {
                    Toast.makeText(this, "Custom progress dismiss callback 1", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        reset_view_progress.setOnClickListener {
            ProgressVisibleControl.init(this).customLayout(null).show(true) {
                Toast.makeText(this, "Normal progress dismiss callback", Toast.LENGTH_SHORT).show()
            }
        }
    }
}