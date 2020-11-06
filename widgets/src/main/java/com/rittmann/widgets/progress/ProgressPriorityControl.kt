package com.rittmann.widgets.progress


/**
 * todo Export to another file if need
 *
 * @see list is the list of progress owners
 * @see active it yet haven't functionality
 * */
class ProgressPriorityControl {

    private var active = false
    private val list = arrayListOf<ProgressModel>()

    fun add(model: ProgressModel) {
        if (model.id.isEmpty()) return

        active = true

        var found = false
        for (l in list)
            if (l.id == model.id) {
                found = true
                break
            }

        if (found.not())
            list.add(model)
    }

    fun remove(model: ProgressModel) {
        for (l in list)
            if (l.id == model.id) {
                list.remove(l)
                if (l.priority == Priority.HIGH) {
                    list.clear()
                }
                break
            }
    }

    /**
     * Check if can hide the progress with this method
     * */
    fun isFreeProgress(): Boolean {
        if (active.not()) return true

        return list.isEmpty()
    }

    class ProgressModel(val id: String, val priority: Priority = Priority.LOW)

    /**
     * @see LOW is the low priority, normal objects
     * @see HIGH is the high priority, when his is removed from the list, the progress needs to be removed
     * */
    enum class Priority {
        LOW, HIGH
    }

    companion object {

        /**
         * TODO: methodName newId or CONTAINS "progress" need be skip
         * */
        fun generateId(): String {
            try {
                if (Throwable().stackTrace.isEmpty()) return ""

                loop@ for (t in Throwable().stackTrace) {
                    when (t.methodName) {
                        "newId", "showProgress", "hideProgress" -> continue@loop
                        else -> return t.methodName
                    }
                }
            } catch (e: Exception) {
            }
            return ""
        }
    }
}