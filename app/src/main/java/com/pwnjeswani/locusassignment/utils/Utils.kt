package com.pwnjeswani.locusassignment.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.pwnjeswani.locusassignment.R
import java.io.InputStream

object Utils {

    /**
     * Write a func to load json from the json local file
     */
    fun loadJson(context: Context): String? {
        var input: InputStream? = null
        var jsonString: String

        try {
            // Create InputStream
            input = context.resources.openRawResource(R.raw.raw_data)

            val size = input.available()

            // Create a buffer with the size
            val buffer = ByteArray(size)

            // Read data from InputStream into the Buffer
            input.read(buffer)

            // Create a json String
            jsonString = String(buffer)
            return jsonString;
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            // Must close the stream
            input?.close()
        }

        return null

    }
}

val Int.dpToInt: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()


fun View.setParams(toggle: Boolean, params: ViewGroup.LayoutParams) {
    params.width = 200.dpToInt.takeIf { !toggle } ?: 100.dpToInt
    params.height = 200.dpToInt.takeIf { !toggle } ?: 100.dpToInt
    this.layoutParams = params
}
fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
    for (grantResult in grantResults) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
            return false
        }
    }
    return true
}
