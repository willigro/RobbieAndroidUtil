package com.rittmann.robbie.support

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object CustomMatchers {
//    fun withHtml(textToTest: String): Matcher<View?>? {
//        return object : BoundedMatcher<View?, TextView?>(TextView::class.java) {
//            override fun matchesSafely(textView: TextView?): Boolean {
//                if (textView == null) return false
//
//                val t = TextView(textView.context).apply {
//                    text = textToTest.fromHtml()
//                }
//                return textView.text.toString() == t.text.toString()
//            }
//
//            override fun describeTo(description: Description) {
//                description.appendText("text with html format")
//            }
//        }
//    }

    fun hasChildren(numChildrenMatcher: Matcher<Int?>): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            /**
             * matching with viewgroup.getChildCount()
             */
            override fun matchesSafely(view: View?): Boolean {
                return view is ViewGroup && numChildrenMatcher.matches(view.childCount)
            }

            /**
             * gets the description
             */
            override fun describeTo(description: Description) {
                description.appendText(" a view with # children is ")
                numChildrenMatcher.describeTo(description)
            }
        }
    }
}