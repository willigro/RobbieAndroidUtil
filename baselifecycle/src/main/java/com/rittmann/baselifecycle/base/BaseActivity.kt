package com.rittmann.baselifecycle.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.baselifecycle.keyboard.KeyboardEventListenerInterface
import com.rittmann.baselifecycle.keyboard.hideKeyboard
import com.rittmann.baselifecycle.keyboard.isKeyboardOpen
import com.rittmann.baselifecycle.keyboard.setKeyboardEventListener
import com.rittmann.widgets.progress.ProgressPriorityControl
import com.rittmann.widgets.progress.ProgressVisibleControl

open class BaseActivity(open var resIdViewReference: Int = 0) : AppCompatActivity() {

    private val progressPriorityControl: ProgressPriorityControl = ProgressPriorityControl()

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

    open fun showProgressPriority(
        priority: ProgressPriorityControl.Priority,
        ignoreId: Boolean = true,
        closeKeyboard: Boolean = false,
        cancelable: Boolean = false,
        dismissCallback: (() -> Unit)? = null
    ): ProgressPriorityControl.ProgressModel {
        progressPriorityControl.configureCallbacksOnStarted {
            showProgress(
                closeKeyboard,
                cancelable,
                dismissCallback
            )
        }

        return progressPriorityControl.add(priority, ignoreId)
    }

    open fun showProgressPriority(
        progressModel: ProgressPriorityControl.ProgressModel,
        closeKeyboard: Boolean = false,
        cancelable: Boolean = false,
        dismissCallback: (() -> Unit)? = null
    ): ProgressPriorityControl.ProgressModel {
        progressPriorityControl.configureCallbacksOnStarted {
            showProgress(
                closeKeyboard,
                cancelable,
                dismissCallback
            )
        }

        return progressPriorityControl.add(progressModel)
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

    open fun hideProgressPriority(
        progressModel: ProgressPriorityControl.ProgressModel,
        closeKeyboard: Boolean = false
    ) {
        progressPriorityControl.configureCallbacksOnCleared {
            Log.i(ProgressPriorityControl.TAG, "hideProgressPriority")
            hideProgress(closeKeyboard)
        }

        progressPriorityControl.remove(progressModel)
    }

    open fun hideProgressPriority(
        priority: ProgressPriorityControl.Priority,
        closeKeyboard: Boolean = false
    ) {
        progressPriorityControl.configureCallbacksOnCleared {
            Log.i(ProgressPriorityControl.TAG, "hideProgressPriority")
            hideProgress(closeKeyboard)
        }

        progressPriorityControl.remove(priority)
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
        baseViewModel.isLoading.observe(this) {
            if (it == true) showProgress()
            else hideProgress()
        }
    }

    fun observeLoadingPriority(baseViewModel: BaseViewModel) {
        baseViewModel.isLoadingPriority.observe(this) { priorityObservable ->
            if (priorityObservable.showing) {
                priorityObservable.model?.also { model ->
                    showProgressPriority(model)
                } ?: kotlin.run {
                    priorityObservable?.priority?.also { priority ->
                        showProgressPriority(priority)
                    }
                }
            } else {
                priorityObservable.model?.also { model ->
                    hideProgressPriority(model)
                } ?: kotlin.run {
                    priorityObservable?.priority?.also { priority ->
                        hideProgressPriority(priority)
                    }
                }
            }
        }
    }
}