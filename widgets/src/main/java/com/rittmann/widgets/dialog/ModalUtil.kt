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

fun Fragment.modal(
    title: String = ModalUtil.defaultTitle,
    message: String,
    cancelable: Boolean = false,
    ok: Boolean = false,
    fromHtml: Boolean = false,
    show: Boolean = false,
    cancelText: String? = null,
    concludeText: String? = null,
    okText: String? = null
): ModalUtil {
    return ModalUtil().init(
        context = requireContext(),
        message = message,
        title = title,
        cancelable = cancelable,
        ok = ok,
        fromHtml = fromHtml,
        show = show,
        cancelText = cancelText,
        concludeText = concludeText,
        okText = okText
    )
}

fun Fragment.modal(modalInternal: ModalInternal): ModalUtil {
    return ModalUtil().init(modalInternal)
}

fun AppCompatActivity.modal(
    title: String = ModalUtil.defaultTitle,
    message: String,
    cancelable: Boolean = false,
    ok: Boolean = false,
    fromHtml: Boolean = false,
    show: Boolean = false,
    cancelText: String? = null,
    concludeText: String? = null,
    okText: String? = null
): ModalUtil {
    return ModalUtil().init(
        context = this,
        message = message,
        title = title,
        cancelable = cancelable,
        ok = ok,
        fromHtml = fromHtml,
        show = show,
        cancelText = cancelText,
        concludeText = concludeText,
        okText = okText
    )
}

fun AppCompatActivity.modal(modalInternal: ModalInternal): ModalUtil {
    return ModalUtil().init(modalInternal)
}

/*
* You can configure the static values using this object
* */
class ModalInternal(
    val context: Context,
    val message: String = "",
    val title: String = ModalUtil.defaultTitle,
    val cancelable: Boolean = false,
    val ok: Boolean = false,
    val fromHtml: Boolean = false,
    val show: Boolean = false,
    val cancelText: String? = null,
    val concludeText: String? = null,
    val okText: String? = null,
    val resIdLayout: Int? = null,
    var resIdBtnConclude: Int? = null,
    var resIdBtnCancel: Int? = null,
    var resIdTitle: Int? = null,
    var resIdMessage: Int? = null,
    var resIdScroll: Int? = null,
    var resIdWebView: Int? = null,
    var useResJustInThisModal: Boolean = false,
    val resetLayout: Boolean = false
) : ModalInternalObject

open class ModalUtil : DialogSimplified {

    private lateinit var context: Context
    lateinit var dialogView: View
    var dialog: AlertDialog? = null

    // Content
    var message: String? = null
    var title: String? = null
    var cancelText: String? = null
    var concludeText: String? = null
    var okText: String? = null

    // Controls
    var cancelable: Boolean = false
    var isOk: Boolean = false
    var fromHtml: Boolean = false
    var useResTemp: Boolean = false

    companion object {
        var defaultTitle: String = ""

        // res
        var resId: Int? = R.layout.dialog_layout
        var resIdBtnConclude: Int? = R.id.btnConclude
        var resIdBtnCancel: Int? = R.id.btnCancel
        var resIdTitle: Int? = R.id.dialogTitleTextView
        var resIdMessage: Int? = R.id.dialogSubtitleTextView
        var resIdScroll: Int? = R.id.scrollContentDialog
        var resIdWebView: Int? = R.id.dialogWebView

        // res temp
        var resIdTemp: Int? = null
        var resIdBtnConcludeTemp: Int? = null
        var resIdBtnCancelTemp: Int? = null
        var resIdTitleTemp: Int? = null
        var resIdMessageTemp: Int? = null
        var resIdScrollTemp: Int? = null
        var resIdWebViewTemp: Int? = null
    }

    @SuppressLint("InflateParams")
    fun init(
        context: Context,
        message: String,
        title: String = defaultTitle,
        cancelable: Boolean = false,
        ok: Boolean = false,
        fromHtml: Boolean = false,
        show: Boolean = false,
        cancelText: String? = null,
        concludeText: String? = null,
        okText: String? = null
    ): ModalUtil {
        this.dialogView = LayoutInflater.from(context).inflate(getResLayout(), null, true)
        this.context = context
        this.cancelable = cancelable
        this.message = message
        this.title = title
        this.cancelText = cancelText
        this.concludeText = concludeText
        this.okText = okText
        this.fromHtml = fromHtml
        this.isOk = ok

        if (show) {
            handleShow()
        }
        return this@ModalUtil
    }

    @SuppressLint("InflateParams")
    fun init(modalInternal: ModalInternal): ModalUtil {
        if (modalInternal.resetLayout)
            resetLayoutConfigurations()
        else
            configureLayout(modalInternal)

        this.context = modalInternal.context
        this.dialogView = LayoutInflater.from(context).inflate(getResLayout(), null, true)
        this.cancelable = modalInternal.cancelable
        this.message = modalInternal.message
        this.title = modalInternal.title
        this.cancelText = modalInternal.cancelText
        this.concludeText = modalInternal.concludeText
        this.okText = modalInternal.okText
        this.fromHtml = modalInternal.fromHtml
        this.isOk = modalInternal.ok

        if (modalInternal.show) {
            handleShow()
        }
        return this@ModalUtil
    }

    private fun getResLayout(): Int {
        if (useResTemp && resIdTemp != null)
            return resIdTemp!!
        return resId ?: R.layout.dialog_layout
    }

    private fun getResIdBtnConclude(): Int {
        if (useResTemp && resIdBtnConcludeTemp != null)
            return resIdBtnConcludeTemp!!
        return resIdBtnConclude ?: R.id.btnConclude
    }

    private fun getResIdBtnCancel(): Int {
        if (useResTemp && resIdBtnCancelTemp != null)
            return resIdBtnCancelTemp!!
        return resIdBtnCancel ?: R.id.btnCancel
    }

    private fun getResIdTitle(): Int {
        if (useResTemp && resIdTitleTemp != null)
            return resIdTitleTemp!!
        return resIdTitle ?: R.id.dialogTitleTextView
    }

    private fun getResIdMessage(): Int {
        if (useResTemp && resIdMessageTemp != null)
            return resIdMessageTemp!!
        return resIdMessage ?: R.id.dialogSubtitleTextView
    }

    private fun getResIdScrollView(): Int {
        if (useResTemp && resIdScrollTemp != null)
            return resIdScrollTemp!!
        return resIdScroll ?: R.id.scrollContentDialog
    }

    private fun getResIdWebView(): Int {
        if (useResTemp && resIdWebViewTemp != null)
            return resIdWebViewTemp!!
        return resIdWebView ?: R.id.dialogWebView
    }

    private fun retrieveCancelText(): String = cancelText ?: context.getString(R.string.cancel_)
    private fun retrieveConcludeText(): String =
        concludeText ?: context.getString(R.string.conclude_)

    private fun retrieveOkText(): String = okText ?: context.getString(R.string.ok_)

    private fun showButton(id: Int, s: String, callback: (() -> Unit)? = null) {
        dialogView.findViewById<AppCompatButton>(id)?.apply {
            text = s
            visibility = View.VISIBLE
            setOnClickListener {
                callback?.invoke()
                dialog?.dismiss()
            }
        }
    }

    private fun handleTitle() {
        title?.also {
            dialogView.findViewById<TextView>(getResIdTitle())?.apply {
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
                dialogView.findViewById<View>(getResIdScrollView())?.visibility = View.VISIBLE
                dialogView.findViewById<TextView>(getResIdMessage())?.apply {
                    visibility = View.VISIBLE
                    text = message
                }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun handleMessageWithHTML(message: String) {
        dialogView.findViewById<WebView>(getResIdWebView())?.apply {
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

    override fun handleShow(
        onClickConclude: (() -> Unit)?,
        onClickCancel: (() -> Unit)?
    ) {
        handleButtons(onClickConclude, onClickCancel)
        handleTitle()
        handleMessage()
        show()
    }

    override fun handleButtons(
        onClickConclude: (() -> Unit)?,
        onClickCancel: (() -> Unit)?
    ) {
        if (this.isOk) {
            showButton(getResIdBtnConclude(), retrieveOkText(), onClickConclude)
        } else {
            showButton(getResIdBtnConclude(), retrieveConcludeText(), onClickConclude)
            showButton(getResIdBtnCancel(), retrieveCancelText(), onClickCancel)
        }
    }

    override fun configureLayout(modalInternalObject: ModalInternalObject) {
        (modalInternalObject as ModalInternal).also { modalInternal ->
            useResTemp = modalInternal.useResJustInThisModal
            modalInternal.resIdLayout?.also { setResIdLayout(it) }
            modalInternal.resIdBtnCancel?.also { setResIdBtnCancel(it) }
            modalInternal.resIdBtnConclude?.also { setResIdBtnConclude(it) }
            modalInternal.resIdTitle?.also { setResIdTitle(it) }
            modalInternal.resIdMessage?.also { setResIdMessage(it) }
            modalInternal.resIdScroll?.also { setResIdScroll(it) }
            modalInternal.resIdWebView?.also { setResIdWebView(it) }
        }
    }

    fun setResIdWebView(id: Int?) {
        if (useResTemp)
            resIdWebViewTemp = id
        else
            resIdWebView = id
    }

    fun setResIdScroll(id: Int?) {
        if (useResTemp)
            resIdScrollTemp = id
        else
            resIdScroll = id
    }

    fun setResIdMessage(id: Int?) {
        if (useResTemp)
            resIdMessageTemp = id
        else
            resIdMessage = id
    }

    fun setResIdTitle(id: Int?) {
        if (useResTemp)
            resIdTitleTemp = id
        else
            resIdTitle = id
    }

    fun setResIdBtnConclude(id: Int?) {
        if (useResTemp)
            resIdBtnConcludeTemp = id
        else
            resIdBtnConclude = id
    }

    fun setResIdBtnCancel(id: Int?) {
        if (useResTemp)
            resIdBtnCancelTemp = id
        else
            resIdBtnCancel = id
    }

    fun setResIdLayout(id: Int?) {
        if (useResTemp)
            resIdTemp = id
        else
            resId = id
    }

    override fun resetLayoutConfigurations() {
        resId = null
        resIdBtnConclude = null
        resIdBtnCancel = null
        resIdTitle = null
        resIdMessage = null
        resIdScroll = null
        resIdWebView = null

        resIdTemp = null
        resIdBtnConcludeTemp = null
        resIdBtnCancelTemp = null
        resIdTitleTemp = null
        resIdMessageTemp = null
        resIdScrollTemp = null
        resIdWebViewTemp = null
    }

    override fun show() {
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

    override fun dismiss() {
        dialog!!.dismiss()
    }

    override fun isShowing(): Boolean {
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