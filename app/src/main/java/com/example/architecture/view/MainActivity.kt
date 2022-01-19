package com.example.architecture.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.architecture.R
import com.example.architecture.databinding.ActivityMainBinding
import com.example.architecture.viewmodel.MainViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay

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
            webChromeClient = ChromeClient()
        }

        webView.loadUrl("https://shopping-skylark.asno-sys.co/default/ay23523")

        val couponName = "税込¥ 税asf込¥ 税込¥ ............. asdf 税込¥ ................. 税asdf込asdf"
        tvText.text = couponName.trim().replace("  ", "")
        tvText.measure(0, 0)
        tvPrice.measure(0, 0)

        val priceWidth = tvPrice.measuredWidth
//        tvText.apply {
//            post {
//                text = couponName
//                val nameWidth = tvText.measuredWidth
//                val end = layout.getLineEnd(0)
//                Log.d("NUMLOG", "end: $end")
//                val start1 = layout.getLineStart(0)
//                val end1 = layout.getLineEnd(0)
//                val start2 = layout.getLineStart(1)
//                val end2 = layout.getLineEnd(1)
//                var countPrice = tvPrice.text.length
//                val countChar1 = end1 - start1
//                val countChar2 = end2 - start2
//
//                val char1Width = nameWidth.toFloat() / countChar1
//                val countCut = (priceWidth / char1Width).toInt()
//                val cutLength = if (countPrice == 0) end2 else end1 * 2 - countCut
//
//                if (countChar2 > 1 && countChar1 <= (countChar2 + countCut)) {
//                    Log.d("NUMLOG", "end2: $end2")
//                    Log.d("NUMLOG", "nameWidth: $nameWidth")
//                    Log.d("NUMLOG", "priceWidth: $priceWidth")
//                    Log.d("NUMLOG", "countCut: $countCut")
//                    Log.d("NUMLOG", "cutLength: $cutLength")
//                    val countNameAfter = "${couponName.substring(0, cutLength - 3)}..."
//                    text = countNameAfter
//                }
//            }
//        }

        val displayMetrics = DisplayMetrics()
        val windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val maxHeight = (0.8f * 0.9f * displayMetrics.heightPixels).toInt()
        val maxWidth = (0.8f * 0.9f * displayMetrics.widthPixels).toInt()
//        image.maxWidth = maxWidth
//        image.maxHeight = maxHeight

        Picasso.get()
            .load("https://www.youtube.com/")
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

    inner class ChromeClient internal constructor() : WebChromeClient() {
        private var fullscreenView: View? = null
        private var rootView: ViewGroup? = null
        private var customViewCallback: CustomViewCallback? = null

        override fun onHideCustomView() {
            if (fullscreenView != null) {

                //Remove fullscreen view from activity root view
                rootView?.removeView(fullscreenView)
                fullscreenView = null

                //Tell browser we did remove fullscreen view
                customViewCallback?.onCustomViewHidden();
                bottomNav.visibility = View.VISIBLE

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }

        }

        override fun onShowCustomView(
            paramView: View,
            paramCustomViewCallback: CustomViewCallback
        ) {
            //Destroy full screen view if already exists
            //Destroy full screen view if already exists
            if (fullscreenView != null) {
                paramCustomViewCallback.onCustomViewHidden()
                return
            }

            //Layout params to fit fullscreen view in our activity

            //Layout params to fit fullscreen view in our activity
            val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //Catch root of current activity to add fullscreen view
            //Catch root of current activity to add fullscreen view
            val viewGroup =
                (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0) as ViewGroup
            rootView = viewGroup

            //Store full screen view, we need it to destroy it out of scope

            //Store full screen view, we need it to destroy it out of scope
            fullscreenView = paramView

            customViewCallback = paramCustomViewCallback
            rootView?.addView(fullscreenView, layoutParams)
            bottomNav.visibility = View.GONE
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        }
    }

    private var isShow = false
    private var dem = 0
    override fun onResume() {
        super.onResume()
        Log.d("LIFECYCLE", "onResume")

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
