package com.rittmann.androidtools.log

import android.util.Log

object LogUtil {

    private const val TAG = "MyPocket"

    fun log(msg: String, tag: String = TAG, logType: LogType = LogType.VERBOSE, clause: Clause = Clause.ALL) {
        when (clause) {
            Clause.DEBUG -> {
                if (BuildConfig.DEBUG) {
                    doLog(logType, tag, msg)
                }
            }
            Clause.ALL -> doLog(logType, tag, msg)
        }
    }

    private fun doLog(type: LogType, tag: String, msg: String) {
        when (type) {
            LogType.VERBOSE -> Log.i(tag, msg)
            LogType.DEBUG -> Log.d(tag, msg)
            LogType.ERROR -> Log.e(tag, msg)
            LogType.WARN -> Log.w(tag, msg)
        }
    }
}

fun String.log(type: LogType = LogType.VERBOSE, clause: Clause = Clause.ALL) {
    LogUtil.log(this, logType = type, clause = clause)
}

fun Int.log(concat: String = "", type: LogType = LogType.VERBOSE, clause: Clause = Clause.ALL) {
    LogUtil.log("$concat$this", logType = type, clause = clause)
}

enum class Clause {
    ALL, DEBUG
}

enum class LogType {
    VERBOSE, DEBUG, ERROR, WARN
}