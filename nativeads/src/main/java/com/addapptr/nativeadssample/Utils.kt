package com.addapptr.nativeadssample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.net.URL

object Utils {

    fun loadBitmap(url: String): Bitmap? {
        var bitmap: Bitmap?
        try {
            val `in` = URL(url).openStream()
            bitmap = BitmapFactory.decodeStream(`in`)
        } catch (t: Exception) {
            bitmap = null
        }
        return bitmap
    }

    @JvmStatic
    fun loadBitmapForView(bitmapUrl: String?, view: ImageView) {
        if (bitmapUrl == null) {
            return
        }

        val downloadThread = Thread {
            val bitmap = loadBitmap(bitmapUrl)
            if (bitmap != null) {
                val h = Handler(Looper.getMainLooper())
                h.post { view.setImageBitmap(bitmap) }
            }
        }
        downloadThread.start()
    }
}