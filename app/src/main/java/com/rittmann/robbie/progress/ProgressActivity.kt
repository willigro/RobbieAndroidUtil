package com.rittmann.robbie.progress

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.robbie.R
import com.rittmann.widgets.progress.ProgressPriorityControl
import com.rittmann.widgets.progress.ProgressVisibleControl
import kotlinx.android.synthetic.main.activity_progress.*
import kotlinx.android.synthetic.main.custom_progress.view.cancel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    var priorityControl = ProgressPriorityControl(
        onStarted = {
            Log.i(ProgressPriorityControl.TAG, "show")
            ProgressVisibleControl.show()
        },
        onCleared = {
            ProgressVisibleControl.hide()
            Log.i(ProgressPriorityControl.TAG, "hide")
        }
    )

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        ProgressVisibleControl.init(this, R.id.base_content)

        normal_progress.setOnClickListener {
            ProgressVisibleControl.show(true) {
                Toast.makeText(this, "Normal progress dismiss callback", Toast.LENGTH_SHORT).show()
            }
        }

        custom_view_progress.setOnClickListener {
            ProgressVisibleControl.init(this, R.id.base_content)
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
            ProgressVisibleControl.init(this, R.id.base_content).customLayout(null).show(true) {
                Toast.makeText(this, "Normal progress dismiss callback", Toast.LENGTH_SHORT).show()
            }
        }

        control_progress.setOnClickListener {
            priorityControl.add(ProgressPriorityControl.Priority.LOW, ignoreId = true)
            loadUntil(1_000, ProgressPriorityControl.Priority.LOW)

            priorityControl.add(ProgressPriorityControl.Priority.LOW, ignoreId = true)
            loadUntil(2_000, ProgressPriorityControl.Priority.LOW)

            priorityControl.add(ProgressPriorityControl.Priority.LOW, ignoreId = true)
            loadUntil(3_000, ProgressPriorityControl.Priority.LOW)
        }

        control_one_high_progress.setOnClickListener {
            priorityControl.add(ProgressPriorityControl.Priority.LOW, ignoreId = true)
            loadUntil(1_000, ProgressPriorityControl.Priority.LOW)

            priorityControl.add(ProgressPriorityControl.Priority.HIGH, ignoreId = true)
            loadUntil(2_000, ProgressPriorityControl.Priority.HIGH)

            priorityControl.add(ProgressPriorityControl.Priority.LOW, ignoreId = true)
            loadUntil(3_000, ProgressPriorityControl.Priority.LOW)
        }

        control_low_different_ids_progress.setOnClickListener {
            val modelOne = priorityControl.add(ProgressPriorityControl.Priority.LOW)
            loadUntilIdOne(1_000, modelOne)

            val modelTwo = priorityControl.add(ProgressPriorityControl.Priority.LOW)
            loadUntilIdTwo(2_000, modelTwo)

            val modelThree = priorityControl.add(ProgressPriorityControl.Priority.LOW)
            loadUntilIdThree(3_000, modelThree)
        }
    }

    private fun loadUntil(
        untilN: Long,
        priority: ProgressPriorityControl.Priority
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load until $untilN")

            priorityControl.remove(priority)
        }
    }

    private fun loadUntilIdOne(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load until $untilN")

            priorityControl.remove(progressModel)
        }
    }

    private fun loadUntilIdTwo(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load until $untilN")

            priorityControl.remove(progressModel)
        }
    }

    private fun loadUntilIdThree(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load until $untilN")

            priorityControl.remove(progressModel)
        }
    }
}