package com.rittmann.robbie.lifecycle.progresskeyboard

import androidx.test.runner.AndroidJUnit4
import com.rittmann.robbie.R
import com.rittmann.robbie.base.BaseActivityImpl
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.ExpressoUtil.checkToast
import com.rittmann.robbie.support.ExpressoUtil.performClick
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgressAndKeyboardTest : ActivityTest() {

    private val delay = 1000L

    @Test
    fun showProgress() {
        getActivity<BaseActivityImpl>().onActivity {
            it.showProgress()
        }
        checkToast("show without hide keyboard")
    }

    @Test
    fun showProgressAndHideKeyboard() {
        val scenario = getActivity<BaseActivityImpl>()
        performClick(R.id.editOne)
        Thread.sleep(delay)
        scenario.onActivity {
            it.showProgress(true)
        }
        checkToast("show with hide keyboard")
    }

    @Test
    fun hideProgress() {
        getActivity<BaseActivityImpl>().onActivity {
            it.hideProgress()
        }
        checkToast("hide without hide keyboard")
    }

    @Test
    fun hideProgressAndHideKeyboard() {
        val scenario = getActivity<BaseActivityImpl>()
        performClick(R.id.editOne)
        Thread.sleep(delay)
        scenario.onActivity {
            it.hideProgress(true)
        }
        checkToast("hide with hide keyboard")
    }
}