package com.rittmann.robbie.lifecycle.progresskeyboard

import androidx.test.runner.AndroidJUnit4
import com.rittmann.robbie.R
import com.rittmann.robbie.base.BaseActivityImpl
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.robbie.support.ExpressoUtil.checkToast
import com.rittmann.robbie.support.ExpressoUtil.checkValue
import com.rittmann.robbie.support.ExpressoUtil.performClick
import com.rittmann.robbie.support.ExpressoUtil.putValue
import com.rittmann.robbie.support.ExpressoUtil.putValueTextView
import com.rittmann.robbie.support.ExpressoUtil.viewDoesNotExists
import com.rittmann.robbie.support.ExpressoUtil.viewNotIsDisplayed
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

<<<<<<< HEAD
        Thread.sleep(delay)
        putValue(R.id.labelProgress, value)
=======
        putValueTextView(R.id.labelProgress, value)
>>>>>>> b1d75859b1dd8f959e0ea9ec9d5c281626cc9ab1

        Thread.sleep(delay)
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