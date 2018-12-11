package com.vietgurus.common.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

object RealPathUtil {

    fun getRealPath(context: Context, uri: Uri): String {
        return when (Build.VERSION.SDK_INT) {
            in 0..Build.VERSION_CODES.JELLY_BEAN_MR2 -> {
                getRealPathFromUriBelowAPI19(context, uri)
            }
            else -> {
                getRealPathFromUriAPI19(context, uri)
            }
        }
    }

    private fun getRealPathFromUriAPI19(context: Context, uri: Uri): String {
        var filePath = ""
        var imageId = ""

        var cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            imageId = cursor.getString(0)
            imageId = imageId.substring(imageId.lastIndexOf(":") + 1)
            cursor.close()
        }

        cursor = context.contentResolver.query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media._ID + " = ? ", arrayOf(imageId), null)
        if (cursor != null) {
            cursor.moveToFirst()
            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            cursor.close()
        }
        return filePath
    }

    private fun getRealPathFromUriBelowAPI19(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, projection, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        } finally {
            cursor?.close()
        }
    }
}