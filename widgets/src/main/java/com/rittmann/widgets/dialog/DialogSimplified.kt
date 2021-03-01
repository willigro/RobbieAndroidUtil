package com.rittmann.widgets.dialog

interface DialogSimplified {

    fun handleShow(
        onClickConclude: (() -> Unit)? = null,
        onClickCancel: (() -> Unit)? = null
    )

    fun handleButtons(
        onClickConclude: (() -> Unit)? = null,
        onClickCancel: (() -> Unit)? = null
    )

    fun configureLayout(modalInternalObject: ModalInternalObject)

    fun resetLayoutConfigurations()

    fun show()

    fun dismiss()

    fun isShowing(): Boolean
}

interface ModalInternalObject {

}