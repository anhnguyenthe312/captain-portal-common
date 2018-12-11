package com.scs71.cws_android.utils.hanler_helper

import android.app.Activity
import android.os.Message

/**
 * Message Handler class that supports buffering up of messages when the
 * activity is paused i.e. in the background.
 */
class BaseRouteHandler : PauseHandler() {
    companion object {
        val MSG_WHAT_DIALOG = ('D'.toInt() shl 16) + ('F'.toInt() shl 8) + 'H'.toInt()
    }

    /**
     * Activity instance
     */
    var activity: Activity? = null

    /**
     * Set the activity associated with the handler
     *
     * @param activity
     * the activity to set
     */

    override fun storeMessage(message: Message): Boolean {
        // All messages are stored by default
        return true
    }

    // handle all kind of message here
    override fun processMessage(message: Message) {
        val activity = this.activity
        if (activity != null) {
            //handle dialog
            if (message.what == MSG_WHAT_DIALOG) {
                DialogHandler.handle(message, activity)
            }
        }
    }
}