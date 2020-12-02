package com.rittmann.widgets.progress

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.widgets.R

/**
 * Able dismiss with onBackPressed
 * */
class Progress(private val activity: AppCompatActivity) {

    private var dialog: AlertDialog? = null
    private var cancelable: Boolean = false
    private var callbackOnPressed: OnBackPressedCallback? = null

    fun show(cancelable: Boolean = false, dismissCallback: (() -> Unit)? = null) {
        if (dialog == null || dialog!!.isShowing.not()) {
            this.cancelable = cancelable

            AlertDialog.Builder(activity).apply {
                setView(getView())
                setCancelable(cancelable)
                dialog = create()
                dialog?.also {
                    it.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog?.show()
                }

                config(dismissCallback)
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
        dialog?.dismiss()
    }

    fun isDismiss(function: () -> Unit) {
        if (dialog == null || dialog!!.isShowing.not()) function()
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