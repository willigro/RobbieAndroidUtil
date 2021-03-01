package com.rittmann.robbie.support

import android.app.Activity
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.idling.CountingIdlingResource
import org.junit.Rule
import org.mockito.Mockito

open class ActivityTest {

    private val mCountingIdlingResource = CountingIdlingResource("GLOBAL")
    protected val targetContext: Context =
            InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val rule = InstantTaskExecutorRule()

    inline fun <reified A : Activity> getActivity(): ActivityScenario<A> {
        return ActivityScenario.launch(A::class.java)
    }

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        mCountingIdlingResource.decrement()
    }

    fun getIdlingResource() = mCountingIdlingResource

    fun getString(resId: Int) = targetContext.getString(resId)

    fun <T> anySuper(type: Class<T>): T = Mockito.any<T>(type)
}