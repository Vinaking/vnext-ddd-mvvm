package com.example.architecture.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.WebSettings
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.architecture.R
import com.example.architecture.databinding.ActivityMainBinding
import com.example.architecture.viewmodel.MainViewModel
import com.example.architecture.viewmodel.ViewModelFactory
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks
import com.google.firebase.inappmessaging.FirebaseInAppMessagingImpressionListener
import com.google.firebase.inappmessaging.display.FirebaseInAppMessagingDisplay
import com.google.firebase.inappmessaging.model.Action
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.inappmessaging.model.MessageType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import android.view.WindowManager

import android.util.DisplayMetrics
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import android.widget.LinearLayout





class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var dataBinding: ActivityMainBinding
    private val webViewClient by lazy { HandlingWebViewClient() }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//        val factory = ViewModelFactory()
//        viewModel = ViewModelProviders.of(this@MainActivity, factory)[MainViewModel::class.java]
//        dataBinding.mainViewModel = viewModel
//        dataBinding.lifecycleOwner = this@MainActivity
//
//        viewModel.isStringEmpty.observe(this@MainActivity, {
//            if (it) Toast.makeText(this@MainActivity, "No content", Toast.LENGTH_SHORT).show()
//        })

//        runBlocking {
//           launch { count() }
//
//
//            Log.d("MainActivity", "runBlocking")
//        }

        webView.apply {
            settings.apply {
                domStorageEnabled = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                javaScriptEnabled = true
            }
            webViewClient = HandlingWebViewClient()
        }

        webView.loadUrl("https://ec-dev.skylark-app.net/visited.php?logined=1")
        val couponName = "税込¥ 税asf込¥ 税込¥ ............. asdf 税込¥ ................. 税asdf込asdf"
        tvText.text = couponName.trim().replace("  ", "")
        tvText.measure(0, 0)
        tvPrice.measure(0, 0)

        val priceWidth = tvPrice.measuredWidth
        tvText.apply {
            post {
                text = couponName
                val nameWidth = tvText.measuredWidth
                val end = layout.getLineEnd(0)
                Log.d("NUMLOG", "end: $end")
                val start1 = layout.getLineStart(0)
                val end1 = layout.getLineEnd(0)
                val start2 = layout.getLineStart(1)
                val end2 = layout.getLineEnd(1)
                var countPrice = tvPrice.text.length
                val countChar1 = end1 - start1
                val countChar2 = end2 - start2

                val char1Width = nameWidth.toFloat() / countChar1
                val countCut = (priceWidth / char1Width).toInt()
                val cutLength = if (countPrice == 0) end2 else end1 * 2 - countCut

                if (countChar2 > 1 && countChar1 <= (countChar2 + countCut)) {
                    Log.d("NUMLOG", "end2: $end2")
                    Log.d("NUMLOG", "nameWidth: $nameWidth")
                    Log.d("NUMLOG", "priceWidth: $priceWidth")
                    Log.d("NUMLOG", "countCut: $countCut")
                    Log.d("NUMLOG", "cutLength: $cutLength")
                    val countNameAfter = "${couponName.substring(0, cutLength - 3)}..."
                    text = countNameAfter
                }
            }
        }

        val displayMetrics = DisplayMetrics()
        val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val maxHeight = (0.8f * 0.9f * displayMetrics.heightPixels).toInt()
        val maxWidth = (0.8f * 0.9f * displayMetrics.widthPixels).toInt()
//        image.maxWidth = maxWidth
//        image.maxHeight = maxHeight

        Picasso.get()
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5Vrmpk0P_-IS1xxxi13qL_q35uEn226d1iA&usqp=CAU")
            .into(image, object : Callback {
                override fun onSuccess() {
//                    val layoutParams: ViewGroup.LayoutParams = image.layoutParams
//                    layoutParams.width = 200
//                    layoutParams.height = 100
//                    image.layoutParams = layoutParams
                }

                override fun onError(e: Exception?) {

                }
            })

    }

    private fun calculateImageView(image: ImageView) {

    }

    private var isShow = false
    private var dem = 0
    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "onResume")
        FirebaseAnalytics.getInstance(this).logEvent("abc_abc", null)
        FirebaseInAppMessaging.getInstance().triggerEvent("abc_abc")

        FirebaseInAppMessaging.getInstance().addClickListener { inAppMessage, action ->
            val link = inAppMessage.action?.actionUrl ?: ""
            this@MainActivity.runOnUiThread { webView.loadUrl(link) }
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

    private suspend fun count() {
        for (i in 0..10) {
            Log.d("MainActivity", "Count: $i")
            delay(1000)
        }
        Log.d("MainActivity", "Count: Done!")
    }

    private suspend fun count2() {
        for (i in 10 downTo 0) {
            Log.d("MainActivity", "Count: $i")
            delay(1000)
        }
        Log.d("MainActivity", "Count: Done!")
    }
}
