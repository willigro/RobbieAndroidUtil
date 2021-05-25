package com.rittmann.baselifecycle.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rittmann.baselifecycle.keyboard.KeyboardEventListenerInterface
import com.rittmann.baselifecycle.keyboard.hideKeyboard
import com.rittmann.baselifecycle.keyboard.isKeyboardOpen
import com.rittmann.baselifecycle.keyboard.setKeyboardEventListener
import com.rittmann.widgets.progress.ProgressVisibleControl

open class BaseActivity : AppCompatActivity() {

    open var resIdViewReference: Int = 0

    open fun showProgress(
        closeKeyboard: Boolean = false,
        cancelable: Boolean = false,
        dismissCallback: (() -> Unit)? = null
    ) {
        ProgressVisibleControl.init(this, resIdViewReference)

        if (closeKeyboard && isKeyboardOpen(findViewById(resIdViewReference))) {
            hideKeyboardAndExecute {
                ProgressVisibleControl.show(cancelable, dismissCallback)
            }
        } else {
            ProgressVisibleControl.show(cancelable, dismissCallback)
        }
    }

    open fun hideProgress(closeKeyboard: Boolean = false) {
        if (closeKeyboard && isKeyboardOpen(findViewById(resIdViewReference))) {
            hideKeyboardAndExecute {
                ProgressVisibleControl.hide()
            }
        } else {
            ProgressVisibleControl.hide()
        }
    }

    private fun hideKeyboardAndExecute(callback: () -> Unit) {
        setKeyboardEventListener(true,
            object : KeyboardEventListenerInterface {
                override fun keyboardIsOpen(isOpen: Boolean) {
                    callback()
                }
            })

        hideKeyboard(this@BaseActivity, findViewById(resIdViewReference))
    }

    fun observeLoading(baseViewModel: BaseViewModel) {
        baseViewModel.isLoading.observe(this, Observer {
            if (it == true) showProgress()
            else hideProgress()
        })
    }
}