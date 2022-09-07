package com.rittmann.widgets.progress

import android.util.Log

/**
 * todo Export to another file if need
 *
 * @see list is the list of progress owners
 * @see active it yet haven't functionality
 * */
class ProgressPriorityControl(
    private var onStarted: () -> Unit = {},
    private var onCleared: () -> Unit = {}
) {

    val list = arrayListOf<ProgressModel>()
    private var active = false
    private var onStartedBlocked = false
    private var onClearedBlocked = false

    fun configureCallbacksOnStarted(onStarted: () -> Unit) {
        Log.i(TAG, "configureCallbacksOnStarted list $onStartedBlocked")
        if (onStartedBlocked.not())
            this.onStarted = onStarted
        onStartedBlocked = true
    }

    fun configureCallbacksOnCleared(onCleared: () -> Unit) {
        if (onClearedBlocked.not())
            this.onCleared = onCleared
        onClearedBlocked = true
    }

    fun add(model: ProgressModel): ProgressModel {
        Log.i(TAG, "Adding list model $model")
        var added = false

        var found = false
        for (l in list) {
            if (l.id == model.id) {
                found = true
                break
            }
        }
        Log.i(TAG, "Adding found $found")

        if (found.not()) {
            list.add(model)
            added = true
        }

        Log.i(TAG, "Adding added $added, list.size=${list.size}")
        if (added && list.size == 1) {
            show()
        }

        return model.apply {
            this.added = added
            Log.i(TAG, "Adding list $list")
        }
    }

    fun add(priority: Priority, ignoreId: Boolean = false): ProgressModel {
        Log.i(TAG, "Adding list priority $priority")
        var model = ProgressModel(priority = priority)
        var added = false

        if (ignoreId) {
            list.add(model)
            added = true
        } else {
            val id = generateId()

            var found = false
            for (l in list)
                if (l.id == id) {
                    found = true
                    break
                }

            if (found.not()) {
                model = model.copy(id = id)
                list.add(model)
                added = true
            }
        }

        if (added && list.size == 1) {
            show()
        }

        return model.apply {
            this.added = added
            Log.i(TAG, "Adding list $list")
        }
    }

    fun remove(
        progressModel: ProgressModel,
        doOnCleared: Boolean = true
    ) {
        Log.i(TAG, "Remove $progressModel")
        if (progressModel.priority == Priority.HIGH) {
            list.clear()
            dismiss()
            return
        }

        for (l in list)
            if (l.id == progressModel.id) {
                list.remove(l)
                Log.i(TAG, "It was removed $progressModel")
                break
            }

        if (doOnCleared && list.isEmpty()) {
            dismiss()
            Log.i(TAG, "Dismiss by $progressModel")
        }
        Log.i(TAG, "List $list")
    }

    fun remove(priority: Priority, doOnCleared: Boolean = true) {
        Log.i(TAG, "Remove $priority")
        if (priority == Priority.HIGH) {
            list.clear()
            dismiss()
            return
        }

        val model = ProgressModel(priority = priority)

        for (l in list)
            if (l.id == model.id) {
                list.remove(l)
                Log.i(TAG, "It was removed $priority")
                break
            }

        if (doOnCleared && list.isEmpty()) {
            dismiss()
            Log.i(TAG, "dismiss by $priority")
        }
        Log.i(TAG, "List $list")
    }

    private fun show() {
        Log.i(TAG, "show active=$active, onStarted=$onStarted")
        if (active.not()) {
            onStarted.invoke()
            active = true
            onStartedBlocked = false
        }
    }

    private fun dismiss() {
        if (active) {
            onCleared.invoke()
            active = false
            onClearedBlocked = false
        }
    }

    /**
     * Check if can hide the progress with this method
     * */
    fun isFreeProgress(): Boolean {
        return list.isEmpty()
    }

    data class ProgressModel(
        val id: String = "",
        val priority: Priority = Priority.LOW,
        var added: Boolean = true
    )

    /**
     * @see LOW is the low priority, normal objects
     * @see HIGH is the high priority, when his is removed from the list, the progress needs to be removed
     * */
    enum class Priority {
        LOW, HIGH
    }

    companion object {

        const val TAG = "PROGRESS"

        /**
         * TODO: methodName newId or CONTAINS "progress" need be skip
         * */
        fun generateId(): String {
            try {
                if (Throwable().stackTrace.isEmpty()) return ""

                loop@ for (t in Throwable().stackTrace) {
                    when (t.methodName) {
                        "generateId", "showProgressPriority", "hideProgressPriority", "hideProgressPriority\$default", "showProgressPriority\$default", "showProgress", "hideProgress", "hide", "add", "add\$default", "remove", "remove\$default" -> continue@loop
                        else -> return "${t.methodName}/${t.lineNumber}"
                    }
                }
            } catch (e: Exception) {
            }
            return ""
        }
    }

    data class PriorityObservable(
        val showing: Boolean,
        val model: ProgressModel? = null,
        val priority: Priority? = null
    )
}