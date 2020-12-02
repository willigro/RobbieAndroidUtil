package com.rittmann.robbie.lifecycle.keyboard

import androidx.test.runner.AndroidJUnit4
import com.rittmann.robbie.base.BaseActivityImpl
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.ExpressoUtil.checkToast
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeyboardTest : ActivityTest() {

    @Test
    fun openActivity() {
        getActivity<BaseActivityImpl>().onActivity {
            checkToast("show without hide keyboard", it)
            it.showProgress()
//            onView(isRoot()).perform(waitFor(1000))

        }
    }
}