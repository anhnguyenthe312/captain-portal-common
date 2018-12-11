package com.vietgurus.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.vietgurus.common.BuildConfig
import java.io.File
import java.io.FileOutputStream

inline fun catchAndLog(action: () -> Unit) {
    try {
        action()
    } catch (t: Throwable) { printStackTrace(t) }
}

fun log(message: String) {
    if (BuildConfig.BUILD_TYPE.toLowerCase() != "release")
        Log.i("DR", message)
}

fun printStackTrace(throwable: Throwable) {
    if (BuildConfig.BUILD_TYPE.toLowerCase() != "release")
        throwable.printStackTrace()
}

fun File.store(bm: Bitmap) {
    if (this.exists()) { this.delete() }
    if (!this.parentFile.exists()) { this.parentFile.mkdirs() }
    try {
        val outStream = FileOutputStream(this)
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
    } catch (e: Exception) { printStackTrace(e) }
}

fun File.store(str: String) {
    if (this.exists()) { this.delete() }
    if (!this.parentFile.exists()) { this.parentFile.mkdirs() }
    try {
        val outStream = FileOutputStream(this)
        outStream.write(str.toByteArray())
        outStream.flush()
        outStream.close()
    } catch (e: Exception) { printStackTrace(e) }
}

fun loadImageBitmap(view: ImageView, imageFile: File) :Bitmap {
    val viewWidth = Math.min(view.width, view.height)
    val viewHeight = Math.max(view.width, view.height)
    val targetWidth = if (viewWidth < 600) 600 else viewWidth
    val targetHeight = if (viewHeight < 800) 800 else viewHeight

    // Get the dimensions of the bitmap
    val bmOptions = BitmapFactory.Options()
    bmOptions.inJustDecodeBounds = true
    BitmapFactory.decodeFile(imageFile.absolutePath, bmOptions)
    val photoW = Math.min( bmOptions.outWidth , bmOptions.outHeight)
    val photoH = Math.max( bmOptions.outWidth , bmOptions.outHeight)
    // Determine how much to scale down the image
    val scaleFactor = Math.max(photoW * 1.0f / targetWidth, photoH * 1.0f / targetHeight)
    bmOptions.inJustDecodeBounds = false
    bmOptions.inSampleSize = (scaleFactor + 0.5f).toInt()

    val bm = BitmapFactory.decodeFile(imageFile.absolutePath, bmOptions)
    view.setImageBitmap(bm)
    return bm
}