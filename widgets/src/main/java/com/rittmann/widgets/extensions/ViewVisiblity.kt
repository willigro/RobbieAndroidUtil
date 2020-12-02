package com.rittmann.widgets.extensions

import android.view.View

fun View?.isVisible() = this?.visibility == View.VISIBLE

fun View?.isGone() = this?.visibility == View.GONE

fun View?.isInvisible() = this?.visibility == View.INVISIBLE

fun View?.isNotVisible() = this?.visibility == View.GONE || this?.visibility == View.INVISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}