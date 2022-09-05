package com.rittmann.widgets.progress

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.widgets.R


/**
 * Able dismiss with onBackPressed
 * */
class Progress(private val activity: AppCompatActivity, private val viewResId: Int) {

    private var dialog: AlertDialog? = null
    private var cancelable: Boolean = false
    private var callbackOnPressed: OnBackPressedCallback? = null

    fun show(cancelable: Boolean = false, dismissCallback: (() -> Unit)? = null) {
        val viewGroup: ViewGroup? = activity.findViewById(viewResId)

        viewGroup?.post {
            if (dialog == null || dialog!!.isShowing.not()) {
                this.cancelable = cancelable

                AlertDialog.Builder(activity, R.style.CustomAlertDialog).apply {
                    setCancelable(cancelable)
                    val displayRectangle = Rect()
                    val window: Window = activity.window
                    window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
                    val dialogView: View = LayoutInflater.from(activity)
                        .inflate(customView ?: R.layout.progress_layout, viewGroup, false)
                    dialogView.minimumWidth = ((displayRectangle.width() * 1f).toInt())
                    dialogView.minimumHeight = ((displayRectangle.height() * 1f).toInt())
                    setView(dialogView)
                    dialog = create()
                    dialog?.show()
                    customViewAccess?.invoke(dialogView)
                    config(dismissCallback)
                }
            }
        }
    }

    private fun config(dismissCallback: (() -> Unit)?) {
        dialog?.setOnDismissListener {
            dismissCallback?.invoke()
        }

        if (cancelable)
            onBackPressedObserver()
    }

    private fun onBackPressedObserver() {
        if (callbackOnPressed == null) {
            callbackOnPressed = object : OnBackPressedCallback(false) {
                override fun handleOnBackPressed() {
                    dismiss()
                }
            }

            activity.onBackPressedDispatcher.addCallback(
                activity,
                callbackOnPressed!!
            )
        }
    }

    @SuppressLint("InflateParams")
    private fun getView(): View? {
        val id = customView ?: R.layout.progress_layout
        return activity.layoutInflater.inflate(id, null).apply {
            if (customView != null) customViewAccess?.invoke(this)
        }
    }

    fun dismiss() {
        val viewGroup: ViewGroup? = activity.findViewById(viewResId)
        viewGroup?.post {
            dialog?.dismiss()
        }
    }

    fun isDismiss(function: () -> Unit) {
        if (dialog == null || dialog!!.isShowing.not() && activity.isFinishing.not()) function()
    }

    companion object {
        private var customViewAccess: ((view: View) -> Unit)? = null
        private var customView: Int? = null
        fun setCustomView(resId: Int?, callback: ((view: View) -> Unit)? = null) {
            customView = resId
            customViewAccess = callback
        }
    }
}