package com.rittmann.robbie.support

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher

object CustomViewActions {
    fun setTextInTextView(value: String?): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(
                        ViewMatchers.isDisplayed(), ViewMatchers.isAssignableFrom(
                        TextView::class.java
                )
                )
            }

            override fun perform(uiController: UiController, view: View) {
                (view as TextView).text = value
            }

            override fun getDescription(): String {
                return "replace text"
            }
        }
    }

    fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }
        }
    }

//    class ScrollToBottomAction : ViewAction {
//        override fun getDescription(): String {
//            return "scroll RecyclerView to bottom"
//        }
//
//        override fun getConstraints(): Matcher<View> {
//            return CoreMatchers.allOf<View>(ViewMatchers.isAssignableFrom(RecyclerView::class.java), ViewMatchers.isDisplayed())
//        }
//
//        override fun perform(uiController: UiController?, view: View?) {
//            val recyclerView = view as RecyclerView
//            val itemCount = recyclerView.adapter?.itemCount
//            val position = itemCount?.minus(1) ?: 0
//            recyclerView.scrollToPosition(position)
//            uiController?.loopMainThreadUntilIdle()
//        }
//    }
}