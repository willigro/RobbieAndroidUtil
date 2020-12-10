package com.rittmann.robbie

import android.os.Bundle
import com.rittmann.baselifecycle.base.BaseActivity
import com.rittmann.widgets.dialog.DialogUtil
import kotlinx.android.synthetic.main.activity_dialogs.btnShowCustomDialog
import kotlinx.android.synthetic.main.activity_dialogs.btnShowDialogs

class DialogsActivity : BaseActivity() {

    override var resIdViewReference: Int = R.id.layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogs)
        initViews()
    }

    private fun initViews() {
        btnShowCustomDialog.setOnClickListener {
            DialogUtil().init(
                this,
                "Testing ok show",
                "Title",
                ok = true,
                show = true,
                resId = R.layout.custom_dialog_layout
            )
        }
        clickToShowSimpleDialogs()
    }

    private fun clickToShowSimpleDialogs() {
        btnShowDialogs.setOnClickListener {
            DialogUtil().init(this, "Testing ok show", "Title", ok = true, show = true)

            DialogUtil().init(
                this,
                "Testing ok cancelable false",
                "Title",
                ok = true,
                show = true,
                cancelable = false
            )

            DialogUtil().init(
                this,
                "Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger textTesting ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger textTesting ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text Testing ok cancelable false with larger text",
                "Title",
                ok = false,
                show = true,
                cancelable = false
            )
        }
    }
}