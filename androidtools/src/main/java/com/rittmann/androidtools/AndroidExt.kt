package com.rittmann.androidtools

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified A : Activity> Context.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}