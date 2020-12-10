package com.rittmann.widgets.extensions

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

fun TextView?.simpleLinkClick(text: String = "", init: Int, finish: Int = -1, click: () -> Unit) {
    if (this == null) return

    val textToUse = if (text.isEmpty()) this.text else text

    val finishToUse = if (finish == -1) textToUse.length else finish

    val spannableString = SpannableString(textToUse)

    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            click()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }

    spannableString.setSpan(
        clickableSpan,
        init,
        finishToUse,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    this.text = spannableString
    movementMethod = LinkMovementMethod.getInstance()
}