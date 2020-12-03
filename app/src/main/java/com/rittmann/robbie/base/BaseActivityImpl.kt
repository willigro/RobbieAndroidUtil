package com.rittmann.robbie.base

import android.os.Bundle
import com.rittmann.baselifecycle.base.BaseActivity
import com.rittmann.robbie.R

class BaseActivityImpl : BaseActivity() {

    override var resIdViewReference: Int = R.id.content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}