package com.rittmann.robbie.progress

import android.util.Log
import com.rittmann.baselifecycle.base.BaseViewModel
import com.rittmann.widgets.progress.ProgressPriorityControl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel : BaseViewModel() {

    fun loadUntil(
        untilN: Long,
        priority: ProgressPriorityControl.Priority
    ) {
        showProgress(priority)

        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $priority until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $priority until $untilN")

            hideProgress(priority, true)
        }
    }

    fun loadUntilModel(
        untilN: Long,
        model: ProgressPriorityControl.ProgressModel,
    ) {
        showProgress(model)

        GlobalScope.launch {
            Log.i(ProgressPriorityControl.TAG, "Starting load by $model until $untilN")
            delay(untilN)
            Log.i(ProgressPriorityControl.TAG, "Finishing load by $model until $untilN")

            hideProgress(model, true)
        }
    }
}