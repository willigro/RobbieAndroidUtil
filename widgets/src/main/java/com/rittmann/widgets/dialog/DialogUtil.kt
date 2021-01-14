package com.rittmann.widgets.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rittmann.widgets.R
import com.rittmann.widgets.comun.WidgestsUtil

fun Fragment.dialog(
    title: String = DialogUtil.defaultTitle,
    message: String,
    cancelable: Boolean = false,
    ok: Boolean = false,
    fromHtml: Boolean = false,
    resId: Int? = null,
    show: Boolean = false
): DialogUtil {
    return DialogUtil().init(
        context = requireContext(),
        message = message,
        title = title,
        cancelable = cancelable,
        ok = ok,
        fromHtml = fromHtml,
        resId = resId,
        show = show
    )
}

fun AppCompatActivity.dialog(
    title: String = DialogUtil.defaultTitle,
    message: String,
    cancelable: Boolean = false,
    ok: Boolean = false,
    fromHtml: Boolean = false,
    resId: Int? = null,
    show: Boolean = false
): DialogUtil {
    return DialogUtil().init(
        context = this,
        message = message,
        title = title,
        cancelable = cancelable,
        ok = ok,
        fromHtml = fromHtml,
        resId = resId,
        show = show
    )
}

/**
 *
 * Layout need views id:

 * [dialogTitleTextView] - Title - TextView REQUIRED
 * [btnCancel] - Negative Button - AppCompatButton REQUIRED
 * [btnConclude] - Positive Button - AppCompatButton REQUIRED

 * [dialogLayout] Layout - LinearLayout
 * [imageDialog] Icon - ImageView
 * [dialogWebView] - Content - WebView
 * [scrollContentDialog] - Wrap Content (dialogSubtitleTextView) - ScrollView
 * [dialogSubtitleTextView] - Content - TextView
 * */
open class DialogUtil {

    private lateinit var context: Context
    lateinit var dialogView: View
    var dialog: AlertDialog? = null
    var message: String? = null
    var title: String? = null
    var fromHtml: Boolean = false
    var cancelable: Boolean = false
    var isOk: Boolean = false
    val defaultListener = View.OnClickListener { dialog?.dismiss() }
    var resId: Int = R.layout.dialog_layout

    companion object {
        var defaultTitle: String = ""
    }

    @SuppressLint("InflateParams")
    fun init(
        context: Context,
        message: String,
        title: String = defaultTitle,
        cancelable: Boolean = false,
        ok: Boolean = false,
        fromHtml: Boolean = false,
        resId: Int? = null,
        show: Boolean = false
    ): DialogUtil {
        resId?.also {
            setResLayout(it)
        }

        this.dialogView = LayoutInflater.from(context).inflate(getResLayout(), null, true)
        this.context = context
        this.cancelable = cancelable
        this.message = message
        this.title = title
        this.fromHtml = fromHtml
        this.isOk = ok

        if (show) {
            handleShow()
        }
        return this@DialogUtil
    }

    private fun getResLayout(): Int = resId

    fun setResLayout(resId: Int) {
        this.resId = resId
    }

    fun handleShow(
        onClickConclude: View.OnClickListener? = defaultListener,
        onClickCancel: View.OnClickListener? = defaultListener
    ) {
        handleButtons(onClickConclude, onClickCancel)
        handleTitle()
        handleMessage()
        show()
    }

    fun handleButtons(
        onClickConclude: View.OnClickListener? = defaultListener,
        onClickCancel: View.OnClickListener? = defaultListener
    ) {
        if (this.isOk) {
            showButton(onClickConclude, R.id.btnConclude, context.getString(R.string.ok_))
        } else {
            showButton(onClickConclude, R.id.btnConclude, context.getString(R.string.conclude_))
            showButton(onClickCancel, R.id.btnCancel, context.getString(R.string.cancel_))
        }
    }

    private fun showButton(onClick: View.OnClickListener?, id: Int, s: String) {
        dialogView.findViewById<AppCompatButton>(id)?.apply {
            text = s
            visibility = View.VISIBLE
            setOnClickListener(onClick)
        }
    }

    private fun handleTitle() {
        title?.also {
            dialogView.findViewById<TextView>(R.id.dialogTitleTextView)?.apply {
                text = it
                visibility = View.VISIBLE
            }
        }
    }

    private fun handleMessage() {
        message?.also { message ->
            if (fromHtml) {
                handleMessageWithHTML(message)
            } else {
                dialogView.findViewById<View>(R.id.scrollContentDialog)?.visibility = View.VISIBLE
                dialogView.findViewById<TextView>(R.id.dialogSubtitleTextView)?.apply {
                    visibility = View.VISIBLE
                    text = message
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun handleMessageWithHTML(message: String) {
        dialogView.findViewById<WebView>(R.id.dialogWebView)?.apply {
            visibility = View.VISIBLE
            settings.javaScriptEnabled = true
            isScrollbarFadingEnabled = true

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    url: String?
                ): Boolean {
                    url?.also { WidgestsUtil.openLinkIntoBrowse(view.context, url) }
                    return true
                }
            }

            val pxSides = WidgestsUtil.convertToPX(16f, TypedValue.COMPLEX_UNIT_PX, context)
            val pxTop = WidgestsUtil.convertToPX(10f, TypedValue.COMPLEX_UNIT_PX, context)
            val head =
                "<head><style type=\"text/css\">" +
//                        "@font-face {font-family: Ubuntu;src: url(\"file:///android_asset/font/ubuntu.ttf\")} " +
//                        "body {font-family: Ubuntu;font-size: medium;color: #5a5a5a; " +
                        "padding: 0; margin-top: ${pxTop}; margin-left: ${pxSides}; margin-right: ${pxSides}; }</style></head>"
            val html = "<html>$head<body>$message</body></html>"
            loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
        }
    }

    protected fun show() {
        val builder = createBuilder()

        dialog = builder.create()
        dialog?.also {
            it.setCanceledOnTouchOutside(cancelable)
            it.window?.setType(TYPE_APPLICATION_PANEL)
            it.show()
        }
        centralizeInWindow()
    }

    private fun createBuilder(): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context, THEME_DEVICE_DEFAULT_LIGHT)
        builder.setView(dialogView)
        builder.setCancelable(false)
        return builder
    }

    fun dismiss() {
        dialog!!.dismiss()
    }

    fun isShowing(): Boolean {
        if (dialog == null)
            return false
        return dialog!!.isShowing
    }

    private fun centralizeInWindow() {
        dialog?.window?.also {
            it.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setGravity(Gravity.CENTER)
        }
    }
}