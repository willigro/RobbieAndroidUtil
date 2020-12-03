package com.rittmann.widgets.progress

import android.view.View
import androidx.appcompat.app.AppCompatActivity

object ProgressVisibleControl {

    private var progress: Progress? = null

    fun init(activity: AppCompatActivity, viewResId: Int): ProgressVisibleControl {
        if (progress == null) progress = Progress(activity, viewResId)
        return this
    }

    fun customLayout(
        resId: Int?,
        callback: ((view: View) -> Unit)? = null
    ): ProgressVisibleControl {
        Progress.setCustomView(resId, callback)
        return this
    }

    /**
     * Will show only one time
     * */
    fun show(cancelable: Boolean = false, dismissCallback: (() -> Unit)? = null) {
        progress?.isDismiss {
            progress?.show(cancelable, dismissCallback)
        }
    }

    fun hide() {
        progress?.dismiss()
    }
}