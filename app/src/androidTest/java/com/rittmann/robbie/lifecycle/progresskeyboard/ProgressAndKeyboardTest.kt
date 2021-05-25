package com.rittmann.robbie.lifecycle.progresskeyboard

import androidx.test.runner.AndroidJUnit4
import com.rittmann.robbie.R
import com.rittmann.robbie.base.BaseActivityImpl
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.EspressoUtil.checkValue
import com.rittmann.robbie.support.EspressoUtil.performClick
import com.rittmann.robbie.support.EspressoUtil.putValueTextView
import com.rittmann.robbie.support.EspressoUtil.viewDoesNotExists
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
        val value = "Showing progress"

        putValueTextView(R.id.labelProgress, value)

        checkValue(R.id.labelProgress, value)
    }

    @Test
    fun showProgressAndHideKeyboard() {
        val scenario = getActivity<BaseActivityImpl>()

        performClick(R.id.editOne)

        scenario.onActivity {
            it.showProgress(true)
        }

        val value = "Showing progress"

        putValueTextView(R.id.labelProgress, value)

        checkValue(R.id.labelProgress, value)
    }

    @Test
    fun hideProgress() {
        val scenario = getActivity<BaseActivityImpl>()

        scenario.onActivity {
            it.showProgress()
        }

        val value = "Hiding progress"

        putValueTextView(R.id.labelProgress, value)

        checkValue(R.id.labelProgress, value)

        scenario.onActivity {
            it.hideProgress()
        }

        viewDoesNotExists(value)
    }

    @Test
    fun hideProgressAndHideKeyboard() {
        val scenario = getActivity<BaseActivityImpl>()

        performClick(R.id.editOne)
        Thread.sleep(delay)

        scenario.onActivity {
            it.showProgress(true)
        }

        val value = "Hiding progress"

        putValueTextView(R.id.labelProgress, value)

        checkValue(R.id.labelProgress, value)

        scenario.onActivity {
            it.hideProgress(true)
        }

        viewDoesNotExists(value)
    }
}