package com.rittmann.robbie.widgets.modals

import com.rittmann.robbie.ModalActivity
import com.rittmann.robbie.R
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.EspressoUtil.checkValue
import com.rittmann.widgets.dialog.ModalInternal
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
}