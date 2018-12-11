package com.vietgurus.common.utils.view_helper

import android.text.Spannable
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.widget.TextView

class Scrolling : ScrollingMovementMethod() {

    override fun onTakeFocus(widget: TextView, text: Spannable, dir: Int) {
        super.onTakeFocus(widget, text, dir)
    }

    override fun onKeyDown(widget: TextView, buffer: Spannable, keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                run {
                    var i = 0
                    val scrollAmount = getScrollAmount(widget)
                    while (i < scrollAmount) {
                        down(widget, buffer)
                        i++
                    }
                }
                return true
            }
            KeyEvent.KEYCODE_DPAD_UP -> {
                var i = 0
                val scrollAmount = getScrollAmount(widget)
                while (i < scrollAmount) {
                    up(widget, buffer)
                    i++
                }
                return true
            }
            else -> return super.onKeyDown(widget, buffer, keyCode, event)
        }
    }

    private fun getScrollAmount(widget: TextView): Int {
        val visibleLineCount = (1f * widget.getHeight() / widget.getLineHeight()) as Int
        var scrollAmount = visibleLineCount - 1
        if (scrollAmount < 1) {
            scrollAmount = 1
        }
        return scrollAmount
    }
}
