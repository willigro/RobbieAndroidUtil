package com.rittmann.robbie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rittmann.androidtools.log.log
import com.rittmann.widgets.dialog.ModalInternal
import com.rittmann.widgets.dialog.modal
import kotlinx.android.synthetic.main.activity_modal.btnShowSimpleModal
import kotlinx.android.synthetic.main.activity_modal.show_modal
import kotlinx.android.synthetic.main.activity_modal.show_modal_change_the_ids_but_just_for_this_instance
import kotlinx.android.synthetic.main.activity_modal.show_modal_configure_default_layout_with_default_ids
import kotlinx.android.synthetic.main.activity_modal.show_modal_configure_default_layout_with_new_ids_and_turn_in_the_default_modal
import kotlinx.android.synthetic.main.activity_modal.show_modal_reset_default
import kotlinx.android.synthetic.main.activity_modal.show_modal_with_internal
import kotlinx.android.synthetic.main.activity_modal.show_modal_without_configure_the_layout_must_show_the_new_default

class ModalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modal)

        show_modal.setOnClickListener {
            showModalWithoutInternal()
        }

        show_modal_with_internal.setOnClickListener {
            showModalWithInternal()
        }

        show_modal_configure_default_layout_with_default_ids.setOnClickListener {
            showModalConfigureTheDefaultLayoutWithDefaultIds()
        }

        show_modal_configure_default_layout_with_new_ids_and_turn_in_the_default_modal.setOnClickListener {
            showModalConfigureTheDefaultLayoutWithNewIdsAndTurnInTheDefaultModal()
        }

        show_modal_change_the_ids_but_just_for_this_instance.setOnClickListener {
            showModalChangeTheIdsButJustForThisInstance()
        }

        show_modal_without_configure_the_layout_must_show_the_new_default.setOnClickListener {
            showModalWithoutConfigureTheLayoutMustShowTheNewDefault()
        }

        show_modal_reset_default.setOnClickListener {
            showModalResetDefault()
        }

        btnShowSimpleModal.setOnClickListener {
            modal(
                message = "Testing",
                onClickCancel = {
                    "logging cancel".log()
                },
                show = true,
                cancelable = true
            ).apply {
                dismissWhenCancelIsClicked = false
                dismissWhenConcludeIsClicked = false
            }
        }
    }

    private fun showModalWithoutConfigureTheLayoutMustShowTheNewDefault() {
        val internal = ModalInternal(
            context = this,
            message = "Teste",
            show = true,
            cancelable = true
        )

        modal(internal)
    }

    private fun showModalChangeTheIdsButJustForThisInstance() {
        val internal = ModalInternal(
            context = this,
            message = "Teste",
            resIdLayout = R.layout.modal_layout_temp,
            resIdBtnCancel = R.id.new_btn_cancel_temp,
            resIdBtnConclude = R.id.new_btn_conclude_temp,
            resIdMessage = R.id.new_modal_message_temp,
            resIdScroll = R.id.new_modal_scroll_temp,
            resIdTitle = R.id.new_modal_title_temp,
            resIdWebView = R.id.new_modal_web_view_temp,
            useResJustInThisModal = true,
            show = true,
            cancelable = true
        )

        modal(internal)
    }

    private fun showModalResetDefault() {
        val internal = ModalInternal(
            context = this,
            message = "Teste",
            cancelable = true,
            resetLayout = true
        )

        modal(internal).apply {
            handleShow()
        }
    }

    private fun showModalConfigureTheDefaultLayoutWithNewIdsAndTurnInTheDefaultModal() {
        val internal = ModalInternal(
            context = this,
            message = "Teste",
            resIdLayout = R.layout.modal_layout_with_new_ids,
            resIdBtnCancel = R.id.new_btn_cancel,
            resIdBtnConclude = R.id.new_btn_conclude,
            resIdMessage = R.id.new_modal_message,
            resIdScroll = R.id.new_modal_scroll,
            resIdTitle = R.id.new_modal_title,
            resIdWebView = R.id.new_modal_web_view,
            show = true,
            cancelable = true
        )

        modal(internal)
    }

    private fun showModalConfigureTheDefaultLayoutWithDefaultIds() {
        val internal = ModalInternal(
            context = this,
            message = "Teste",
            resIdLayout = R.layout.modal_layout_with_default_ids,
            show = true,
            cancelable = true
        )

        modal(internal)
    }

    private fun showModalWithInternal() {
        val internal = ModalInternal(
            context = this,
            message = "Teste",
            show = true,
            cancelable = true
        )

        modal(internal)
    }

    private fun showModalWithoutInternal() {
        modal(
            title = "",
            message = "",
            show = true,
            cancelable = true
        )
    }
}