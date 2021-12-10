package com.example.navigation_ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class NavgationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val arr = arrayOf("a", "b", "c", "d", "1", "3")

        val list = ArrayList<String>()
        list.addAll(arr)

        list.runCatching {
            add("d")
            removeAt(9)
            Log.d("RunLog", "${size}")
        }.fold(
            onSuccess = {},
            onFailure = {}
        )

        var ints = arr.mapNotNull { a ->
            a.toIntOrNull()
        }

        Log.d("RunLog", "$ints")

        val bunde = Bundle()

        val fragment = StartFragment()
        fragment.arguments = bunde
    }
}