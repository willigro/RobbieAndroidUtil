package com.rittmann.robbie.widgets.progress

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.runner.AndroidJUnit4
import com.rittmann.robbie.BlankFragment
import com.rittmann.robbie.support.ActivityTest
import com.rittmann.widgets.progress.ProgressVisibleControl
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgressVisibilityTest : ActivityTest() {

    @Test
    fun showProgress() {
        launchFragmentInContainer<BlankFragment>().onFragment { fragment ->
            fragment.apply {
                ProgressVisibleControl.init(requireActivity() as AppCompatActivity)
                ProgressVisibleControl.show(false)
            }
        }
    }
}