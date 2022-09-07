package com.rittmann.robbie.progress

import android.os.Bundle
import android.util.Log
import com.rittmann.baselifecycle.base.BaseActivity
import com.rittmann.robbie.R
import com.rittmann.widgets.progress.ProgressPriorityControl
import kotlinx.android.synthetic.main.activity_progress.control_low_different_ids_progress
import kotlinx.android.synthetic.main.activity_progress.control_one_high_progress
import kotlinx.android.synthetic.main.activity_progress.control_progress
import kotlinx.android.synthetic.main.activity_progress_base.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressBaseActivity : BaseActivity(R.id.container) {

    private val viewModel: ProgressViewModel = ProgressViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_base)

        initView()
        initObservers()
    }

    private fun initView() {
        control_progress.setOnClickListener {
            showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntil(1_000, ProgressPriorityControl.Priority.LOW)

            showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntil(2_000, ProgressPriorityControl.Priority.LOW)

            showProgressPriority(ProgressPriorityControl.Priority.HIGH)
            loadUntil(3_000, ProgressPriorityControl.Priority.HIGH)

            showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntil(4_000, ProgressPriorityControl.Priority.LOW)
        }

        control_one_high_progress.setOnClickListener {
            val modelOne = showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntil(1_000, modelOne)

            showProgressPriority(ProgressPriorityControl.Priority.HIGH)
            loadUntil(2_000, ProgressPriorityControl.Priority.HIGH)

            val modelTwo = showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntil(3_000, modelTwo)
        }

        control_low_different_ids_progress.setOnClickListener {
            val modelOne = showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntilIdOne(1_000, modelOne)

            val modelTwo = showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntilIdTwo(2_000, modelTwo)

            val modelThree = showProgressPriority(ProgressPriorityControl.Priority.LOW)
            loadUntilIdThree(3_000, modelThree)
        }

        control_low_using_view_model.setOnClickListener {
//            viewModel.loadUntil(10_000, ProgressPriorityControl.Priority.LOW)

            viewModel.loadUntilModel(1_000, ProgressPriorityControl.ProgressModel("1"))
            viewModel.loadUntilModel(3_000, ProgressPriorityControl.ProgressModel("2"))

//            viewModel.loadUntil(3_000, ProgressPriorityControl.Priority.LOW)
        }
    }

    private fun initObservers() {
        observeLoadingPriority(viewModel)
    }

    private fun loadUntil(
        untilN: Long,
        priority: ProgressPriorityControl.Priority
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $priority until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $priority until $untilN")

            hideProgressPriority(priority)
        }
    }

    private fun loadUntil(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $progressModel until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $progressModel until $untilN")

            hideProgressPriority(progressModel)
        }
    }

    private fun loadUntilIdOne(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $progressModel until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $progressModel until $untilN")

            hideProgressPriority(progressModel)
        }
    }

    private fun loadUntilIdTwo(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $progressModel until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $progressModel until $untilN")

            hideProgressPriority(progressModel)
        }
    }

    private fun loadUntilIdThree(
        untilN: Long,
        progressModel: ProgressPriorityControl.ProgressModel
    ) {
        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $progressModel until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $progressModel until $untilN")

            hideProgressPriority(progressModel)
        }
    }
}