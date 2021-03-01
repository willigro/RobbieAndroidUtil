package com.rittmann.robbie.widgets.modals

import com.rittmann.robbie.ModalActivity
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.ExpressoUtil.checkValue
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

        checkValue("")
    }
}