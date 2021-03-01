package com.rittmann.robbie.support.drawable

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

open class DrawableMatcher internal constructor(@field:DrawableRes @param:DrawableRes private val mDrawableId: Int, @Nullable expectedColorFilter: ColorFilter, extractor: Extractor? = null) : TypeSafeMatcher<View?>() {
    interface Extractor {
        @NonNull
        fun extract(@NonNull v: View?): Drawable?
    }

    private var mReason: String? = null
    private val mExpectedColorFilter: ColorFilter = expectedColorFilter
    private val mExtractor: Extractor? = extractor
    override fun matchesSafely(target: View?): Boolean {
        if (target == null || target !is ImageView && mExtractor == null) {
            mReason = "view " + target!!.id.toString() + " is not an " + ImageView::class.java.simpleName + ". Pass in a custom Extractor in order to use this matcher."
            return false
        }
        val actualDrawable: Drawable? = if (mExtractor == null) defaultExtract(target) else mExtractor.extract(target)
        if (mDrawableId < 0 && actualDrawable != null) {
            mReason = "expected no drawable for view " + target.id.toString() + ", but has one"
            return false
        }
        val expectedDrawable: Drawable? = ViewTestUtil.drawableById(target.context, mDrawableId)
        if (expectedDrawable == null) {
            mReason = "drawable with id $mDrawableId does not exist"
            return false
        }
        expectedDrawable.colorFilter = mExpectedColorFilter
        if (actualDrawable == null) {
            mReason = "actual is null"
            return false
        }
        val actualBmp = ViewTestUtil.getBitmap(actualDrawable)
        if (!ViewTestUtil.getBitmap(expectedDrawable, actualBmp.width, actualBmp.height).sameAs(actualBmp)) {
            mReason = "expected and actual bitmaps do not match"
            return false
        }
        return true
    }

    override fun describeTo(description: Description) {
        description.appendText(if (mReason == null) "" else mReason)
    }

    companion object {
        private fun defaultExtract(target: View): Drawable {
            val targetImageView: ImageView = target as ImageView
            return targetImageView.drawable
        }
    }

}

object ViewTestUtil {
    fun getBitmap(drawable: Drawable, w: Int? = null, h: Int? = null): Bitmap {
        val wd = w ?: drawable.bounds.width()
        val hd = h ?: drawable.bounds.height()
        val bitmap: Bitmap = Bitmap.createBitmap(wd, hd, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun drawableById(context: Context, @DrawableRes id: Int): Drawable? {
        val drawable: Drawable? = ContextCompat.getDrawable(context, id)
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && drawable != null) DrawableCompat.wrap(drawable).mutate() else drawable
    }
}