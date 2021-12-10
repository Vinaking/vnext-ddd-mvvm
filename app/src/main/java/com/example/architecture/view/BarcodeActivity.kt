package com.example.architecture.view

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.architecture.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.pdf417.encoder.BarcodeMatrix
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_barcode.*
import java.net.URLEncoder.encode

class BarcodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        val text = "ABC-abc-1234"
        tvBarcode.text = text
        val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, screenRectPx.width(),180)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width)
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x,y)) Color.BLACK else Color.WHITE)
            }

        ivBarcode.setImageBitmap(bitmap)
    }
}