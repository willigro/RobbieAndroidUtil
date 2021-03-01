package com.rittmann.robbie.widgets.modals

import com.rittmann.robbie.ModalActivity
import com.rittmann.robbie.R
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.EspressoUtil.backgroundColor
import com.rittmann.robbie.support.EspressoUtil.checkValue
import com.rittmann.widgets.dialog.ModalInternal
import com.rittmann.widgets.dialog.ModalUtil
import com.rittmann.widgets.dialog.modal
import org.junit.Test

class ModalActivityTest : ActivityTest() {

    @Test
    fun showModal_WithoutChangeTheLayout_WithoutUseInternalModal() {
        val title = "title"
        val message = "Message"
        val cancelText = "Text cancel"
        val concludeText = "Text conclude"

        getActivity<ModalActivity>().onActivity {
            it.modal(
                title = title,
                message = message,
                cancelText = cancelText,
                concludeText = concludeText,
                show = true
            )
        }

        checkValue(R.id.dialogTitleTextView, title)
        checkValue(R.id.dialogSubtitleTextView, message)
        checkValue(R.id.btnCancel, cancelText)
        checkValue(R.id.btnConclude, concludeText)
    }

    @Test
    fun showModal_WithoutChangeTheLayout_UsingInternalModal() {
        val title = "modal title"
        val message = "modal Message"
        val cancelText = "Text cancel"
        val concludeText = "Text conclude"

        getActivity<ModalActivity>().onActivity {
            val internal = ModalInternal(
                context = it,
                title = title,
                message = message,
                cancelText = cancelText,
                concludeText = concludeText,
                show = true
            )

            it.modal(internal)
        }

        checkValue(R.id.dialogTitleTextView, title)
        checkValue(R.id.dialogSubtitleTextView, message)
        checkValue(R.id.btnCancel, cancelText)
        checkValue(R.id.btnConclude, concludeText)
    }

    @Test
    fun showModal_ChangeLayoutJustInOneInstance_UsingInternalModal() {
        val title = "modal title"
        val message = "modal Message"
        val cancelText = "Text cancel"
        val concludeText = "Text conclude"

        val activity = getActivity<ModalActivity>().onActivity {
            val internalChangeLayout = ModalInternal(
                context = it,
                title = title,
                message = message,
                cancelText = cancelText,
                concludeText = concludeText,
                show = true,
                resIdLayout = R.layout.modal_layout_with_new_ids,
                resIdTitle = R.id.new_modal_title,
                resIdMessage = R.id.new_modal_message,
                resIdBtnCancel = R.id.new_btn_cancel,
                resIdBtnConclude = R.id.new_btn_conclude,
                useResJustInThisModal = true // don`t configure this id that default
            )

            it.modal(internalChangeLayout)
        }

        checkValue(R.id.new_modal_title, title)
        checkValue(R.id.new_modal_message, message)
        checkValue(R.id.new_btn_cancel, cancelText)
        checkValue(R.id.new_btn_conclude, concludeText)
        backgroundColor(R.id.new_modal_layout, android.R.color.holo_orange_dark)

        activity.onActivity {
            val internalDefaultLayout = ModalInternal(
                context = it,
                title = title,
                message = message,
                cancelText = cancelText,
                concludeText = concludeText,
                show = true
            )

            it.modal(internalDefaultLayout)
        }

        checkValue(R.id.dialogTitleTextView, title)
        checkValue(R.id.dialogSubtitleTextView, message)
        checkValue(R.id.btnCancel, cancelText)
        checkValue(R.id.btnConclude, concludeText)
        backgroundColor(R.id.dialogLayout, android.R.color.white)
    }

    @Test
    fun showModal_SetTheDefaultLayout_UsingInternalModal() {
        val title = "modal title"
        val message = "modal Message"
        val cancelText = "Text cancel"
        val concludeText = "Text conclude"

        var modalOne: ModalUtil? = null

        val activity = getActivity<ModalActivity>().onActivity {
            val internalChangeTheDefaultLayout = ModalInternal(
                context = it,
                title = title,
                message = message,
                cancelText = cancelText,
                concludeText = concludeText,
                show = true,
                resIdLayout = R.layout.modal_layout_with_new_ids,
                resIdTitle = R.id.new_modal_title,
                resIdMessage = R.id.new_modal_message,
                resIdBtnCancel = R.id.new_btn_cancel,
                resIdBtnConclude = R.id.new_btn_conclude,
                useResJustInThisModal = false // configure the resId as default
            )

            modalOne = it.modal(internalChangeTheDefaultLayout)
        }

        checkValue(R.id.new_modal_title, title)
        checkValue(R.id.new_modal_message, message)
        checkValue(R.id.new_btn_cancel, cancelText)
        checkValue(R.id.new_btn_conclude, concludeText)
        backgroundColor(R.id.new_modal_layout, android.R.color.holo_orange_dark)

        modalOne?.dismiss()

        activity.onActivity {
            val internalDefaultLayout = ModalInternal(
                context = it,
                title = title,
                message = message,
                cancelText = cancelText,
                concludeText = concludeText,
                show = true
            )

            it.modal(internalDefaultLayout)
        }

        checkValue(R.id.new_modal_title, title)
        checkValue(R.id.new_modal_message, message)
        checkValue(R.id.new_btn_cancel, cancelText)
        checkValue(R.id.new_btn_conclude, concludeText)
        backgroundColor(R.id.new_modal_layout, android.R.color.holo_orange_dark)
    }
}