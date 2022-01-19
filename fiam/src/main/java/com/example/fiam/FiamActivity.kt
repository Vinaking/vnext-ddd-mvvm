package com.example.fiam

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.display.FirebaseInAppMessagingDisplay
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.inappmessaging.model.MessageType

class FiamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fiam)
    }

    override fun onResume() {
        super.onResume()
        FirebaseInAppMessaging.getInstance().triggerEvent("abc_abc")

        FirebaseInAppMessaging.getInstance().addClickListener { inAppMessage, action ->
            val link = inAppMessage.action?.actionUrl ?: ""
        }
//        FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true)
//        FirebaseInAppMessaging.getInstance().clearDisplayListener()

        FirebaseInAppMessaging.getInstance()
            .setMessageDisplayComponent() { inAppMessage, callbacks ->
                openInAppMessaging(inAppMessage, callbacks)
            }
////
//        FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true)


//        FirebaseInAppMessaging.getInstance()
//            .setMessageDisplayComponent { inAppMessage, callbacks ->
//                inAppMessage.messageType?.let {
//                    openInAppMessaging(inAppMessage, callbacks)
//                }
//            }
    }

    private fun openInAppMessaging(
        message: InAppMessage,
        callbacks: FirebaseInAppMessagingDisplayCallbacks
    ) {
        when (message.messageType) {
            MessageType.CARD -> {
//                InAppMessagingDialogFragment.apply {
//                    dismiss(activity = this@MainActivity, tag = TAG_IN_APP_MESSAGING)
//                    show(
//                        activity = this@MainActivity,
//                        tag = TAG_IN_APP_MESSAGING,
//                        title = getTitleInAppMessaging(message),
//                        message = getContentInAppMessaging(message),
//                        imageUrl = getImageUrlInAppMessaging(message),
//                        primary = getPrimaryTextInAppMessaging(message)
//                            ?: getString(R.string.cmn_ok_jp),
//                        second = getSecondTextInAppMessaging(message)
//                            ?: getString(R.string.cmn_close),
//                        cancelable = false,
//                        primaryAction = getActionInAppMessaging(message),
//                        secondAction = getActionInAppMessaging(message, isSecond = true)
//                    )
//                    callbacks.impressionDetected()
//                }

                callbacks.impressionDetected()

                val builder = AlertDialog.Builder(this)
                builder.setMessage("Bạn có chắc chắn muốn thoát không?")
                builder.setCancelable(false)
                builder.setPositiveButton("Có") { dialog: DialogInterface, _: Int ->
                    super.onBackPressed()
                    dialog.cancel()
                }
                builder.setNegativeButton("Không") { dialog: DialogInterface, _: Int ->

                    dialog.cancel()
                }
                builder.create().show()
            }

            MessageType.MODAL, MessageType.BANNER, MessageType.IMAGE_ONLY -> {
                FirebaseInAppMessagingDisplay.getInstance().testMessage(this, message,
                    object : FirebaseInAppMessagingDisplayCallbacks {
                        override fun impressionDetected(): Task<Void> {
                            return callbacks.impressionDetected()
                        }

                        override fun messageDismissed(p0: FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType): Task<Void> {
                            return callbacks.messageDismissed(p0)
                        }

                        override fun messageClicked(p0: Action): Task<Void> {
                            return callbacks.messageClicked(p0)
                        }

                        override fun displayErrorEncountered(p0: FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason): Task<Void> {
                            return callbacks.displayErrorEncountered(p0)
                        }
                    })
            }
            else -> {
            }
        }
    }
}