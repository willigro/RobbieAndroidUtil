package com.rittmann.baselifecycle.base

import androidx.appcompat.app.AppCompatActivity
import com.rittmann.baselifecycle.keyboard.KeyboardEventListenerInterface
import com.rittmann.baselifecycle.keyboard.hideKeyboard
import com.rittmann.baselifecycle.keyboard.isKeyboardOpen
import com.rittmann.baselifecycle.keyboard.setKeyboardEventListener

class BaseActivity : AppCompatActivity() {

    open var resIdViewReference: Int = 0

    open fun showProgress(closeKeyboard: Boolean = false) {
        if (closeKeyboard && isKeyboardOpen(findViewById(resIdViewReference))) {
            hideKeyboardAndExecute {
                // todo implement show
            }
        } else {
            // todo implement show
        }
    }

    open fun hideProgress(closeKeyboard: Boolean = false) {
        if (closeKeyboard && isKeyboardOpen(findViewById(resIdViewReference))) {
            hideKeyboardAndExecute {
                // todo implement hide
            }
        } else {
            // todo implement hide
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
}