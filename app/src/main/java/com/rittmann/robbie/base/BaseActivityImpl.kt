package com.rittmann.robbie.base

import android.os.Bundle
import com.rittmann.baselifecycle.base.BaseActivity
import com.rittmann.robbie.R
import com.rittmann.widgets.progress.ProgressVisibleControl

class BaseActivityImpl : BaseActivity() {

    override var resIdViewReference: Int = R.id.content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        ProgressVisibleControl.customLayout(R.layout.custom_progress)
    }
}