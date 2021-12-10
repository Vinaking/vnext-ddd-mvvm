package com.example.architecture.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.architecture.R
import com.example.architecture.databinding.ActivityMainBinding
import com.example.architecture.viewmodel.MainViewModel
import com.example.architecture.viewmodel.ViewModelFactory
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.FirebaseInAppMessagingImpressionListener
import com.google.firebase.inappmessaging.display.FirebaseInAppMessagingDisplay
import com.google.firebase.inappmessaging.model.InAppMessage
import com.google.firebase.inappmessaging.model.MessageType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var dataBinding: ActivityMainBinding

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

    }

    private var isShow = false
    private var dem = 0
    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "onResume")
        FirebaseAnalytics.getInstance(this).logEvent("abc_abc", null)
        FirebaseInAppMessaging.getInstance().triggerEvent("abc_abc")
        FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true)
        FirebaseInAppMessaging.getInstance().clearDisplayListener()
//        if (!isShow) {
//            Log.d("LIFECYCLE", "isShow")
//            FirebaseInAppMessaging.getInstance().apply {
//                setMessageDisplayComponent { inAppMessage, _ ->
//                    when (inAppMessage.messageType) {
//                        MessageType.CARD -> {
//
//                        }
//                        else -> {
//                            isShow = true
//                            Toast.makeText(
//                                this@MainActivity,
//                                inAppMessage.messageType.toString(),
//                                Toast.LENGTH_LONG
//                            ).show()
//                            Log.d("LIFECYCLE", "${inAppMessage.messageType}")
//                        }
//                    }
//
//                }
//            }
//        }
////
//        Handler().postDelayed(
//            {
//                if (isShow && dem == 0) {
//                    dem = 1
//                    onResume()
//                }
//
//            }, 1000)

//        Handler().postDelayed({
//            FirebaseInAppMessaging.getInstance().triggerEvent("abc_abc")
////            FirebaseInAppMessaging.getInstance().setMessagesSuppressed(false)
//        }, 1000)

        FirebaseInAppMessaging.getInstance().apply {

            removeClickListener{ it, _ ->
                Log.d("FIAM", "impression log: ${it.messageType}")
            }

            addDismissListener {
                Log.d("FIAM", "impression log: ${it.messageType}")
            }

            addDisplayErrorListener { inAppMessage, inAppMessagingErrorReason ->
                Log.d("FIAM", "impression log: ${inAppMessage.messageType}")
            }

            addImpressionListener {
//            FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true)
                Log.d("FIAM", "impression log: ${it.messageType}")
            }

            addClickListener { inAppMessage, action ->
                val string = action.actionUrl
                Log.d("FIAM", "action: ${inAppMessage.campaignMetadata?.campaignName}")
                Log.d("FIAM", "action: ${action.actionUrl}")
            }
        }

//        FirebaseInAppMessaging.getInstance().setMessageDisplayComponent() { inAppMessage, callbacks ->
//            val messageType = inAppMessage.messageType
//            messageType?.let { type ->
//                when (type) {
//                    MessageType.CARD -> {
//                        Toast.makeText(this@MainActivity, "CARD", Toast.LENGTH_LONG).show()
//                    }
//                    MessageType.BANNER -> {
//                        Toast.makeText(this@MainActivity, "BANNER", Toast.LENGTH_LONG).show()
//                    }
//                    MessageType.MODAL -> {
//                        Toast.makeText(this@MainActivity, "MODAL", Toast.LENGTH_LONG).show()
//                    }
//                    MessageType.IMAGE_ONLY -> {
//                        Toast.makeText(this@MainActivity, "IMAGE_ONLY", Toast.LENGTH_LONG).show()
//                    }
//                    else -> {
//                        Toast.makeText(this@MainActivity, "else", Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }
////
//        FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true)

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
