package com.example.architecture.view

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplay
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.inappmessaging.model.MessageType

class MyFIAMDisplay(private val context: Context): FirebaseInAppMessagingDisplay, Application.ActivityLifecycleCallbacks {
    override fun displayMessage(p0: InAppMessage, p1: FirebaseInAppMessagingDisplayCallbacks) {
        Toast.makeText(context, p0.messageType.toString(), Toast.LENGTH_LONG).show()
        Log.d("FIAM", "${p0.messageType}")
//        when(p0.messageType) {
//            MessageType.CARD -> {
//
//            }
//        }

    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Log.d("FIAM", "onActivityCreated")
    }

    override fun onActivityStarted(p0: Activity) {
        Log.d("FIAM", "onActivityStarted")
    }

    override fun onActivityResumed(p0: Activity) {
        Log.d("FIAM", "onActivityResumed")
    }

    override fun onActivityPaused(p0: Activity) {
        Log.d("FIAM", "onActivityPaused")
    }

    override fun onActivityStopped(p0: Activity) {
        Log.d("FIAM", "onActivityStopped")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        Log.d("FIAM", "onActivitySaveInstanceState")
    }

    override fun onActivityDestroyed(p0: Activity) {
        Log.d("FIAM", "onActivityDestroyed")
    }
}