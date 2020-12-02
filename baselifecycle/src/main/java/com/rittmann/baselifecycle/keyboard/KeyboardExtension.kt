package com.rittmann.baselifecycle.keyboard

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}

fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = this.convertDpToPx(50F).roundToInt()
    return heightDiff > marginOfError
}

fun Activity.isKeyboardOpen(view: View?): Boolean {
    if (view == null) return false

    val visibleBounds = Rect()
    view.getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = view.height - visibleBounds.height()
    val marginOfError = this.convertDpToPx(50F).roundToInt()
    return heightDiff > marginOfError
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}

fun AppCompatActivity.setKeyboardEventListener(
    context: AppCompatActivity,
    unregisterOnChange: Boolean = false,
    keyboardIsOpen: () -> Unit
) {
    KeyboardEventListener(context, unregisterOnChange) {
        keyboardIsOpen()
    }
}

fun AppCompatActivity.setKeyboardEventListener(
    unregisterOnChange: Boolean = false,
    listener: KeyboardEventListenerInterface
) {
    KeyboardEventListener(this, unregisterOnChange) {
        listener.keyboardIsOpen(it)
    }
}

fun AppCompatActivity.showKeyboard(context: Context, view: View?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    view?.let { imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT) }
}

fun AppCompatActivity.hideKeyboard(context: Context, view: View?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

interface KeyboardEventListenerInterface {
    fun keyboardIsOpen(isOpen: Boolean)
}