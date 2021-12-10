package com.example.architecture.view

import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import android.graphics.Bitmap
import android.webkit.HttpAuthHandler


class HandlingWebViewClient: WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d(
            "WebViewClient",
            "onPageStarted"
        )
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        return handleUrlIfNeed(request.url)
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return handleUrlIfNeed(Uri.parse(url))
    }

    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        when (host) {
            "ec-dev.skylark-app.net" -> {
                handler?.proceed("skylark", "SkYlArK")
            }
        }
    }

    private fun handleUrlIfNeed(uri: Uri): Boolean {

        val host = uri.host  // ホストだけ
        val path = uri.path ?: ""  // ホスト抜かしたパス部分のみ
        val urlFullPath = uri.toString()  // URLの全て
        val scheme = uri.scheme


        Log.d(
            "WebViewClient",
            "handleUrlIfNeed host=$host"
        )

        Log.d(
            "WebViewClient",
            "handleUrlIfNeed scheme=$scheme"
        )

        Log.d(
            "WebViewClient",
            "handleUrlIfNeed path=$path"
        )

        Log.d(
            "WebViewClient",
            "handleUrlIfNeed urlFullPath=$urlFullPath"
        )

        return true
    }
}