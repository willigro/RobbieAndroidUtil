package com.rittmann.baselifecycle.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rittmann.baselifecycle.keyboard.KeyboardEventListenerInterface
import com.rittmann.baselifecycle.keyboard.hideKeyboard
import com.rittmann.baselifecycle.keyboard.isKeyboardOpen
import com.rittmann.baselifecycle.keyboard.setKeyboardEventListener

open class BaseActivity : AppCompatActivity() {

    open var resIdViewReference: Int = 0

    open fun showProgress(closeKeyboard: Boolean = false) {
        if (closeKeyboard && isKeyboardOpen(findViewById(resIdViewReference))) {
            hideKeyboardAndExecute {
                Toast.makeText(this@BaseActivity, "show with hide keyboard", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this@BaseActivity, "show without hide keyboard", Toast.LENGTH_SHORT)
                .show()
        }
    }

    open fun hideProgress(closeKeyboard: Boolean = false) {
        if (closeKeyboard && isKeyboardOpen(findViewById(resIdViewReference))) {
            hideKeyboardAndExecute {
                Toast.makeText(this@BaseActivity, "show with show keyboard", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this@BaseActivity, "show without show keyboard", Toast.LENGTH_SHORT)
                .show()
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