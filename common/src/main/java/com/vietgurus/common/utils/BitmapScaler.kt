package com.vietgurus.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream
import java.io.IOException


class BitmapScaler {
    companion object {

        // Scale and maintain aspect ratio given a desired width
        // BitmapScaler.scaleToFitWidth(bitmap, 100);
        @JvmStatic
        fun scaleToFitWidth(b: Bitmap, width: Int): Bitmap {
            val factor = width / b.getWidth() as Float
            return Bitmap.createScaledBitmap(b, width, (b.getHeight() * factor) as Int, true)
        }

        // Scale and maintain aspect ratio given a desired height
        // BitmapScaler.scaleToFitHeight(bitmap, 100);
        @JvmStatic
        fun scaleToFitHeight(b: Bitmap, height: Int): Bitmap {
            val factor = height / b.getHeight() as Float
            return Bitmap.createScaledBitmap(b, (b.getWidth() * factor) as Int, height, true)
        }

        fun scale(imageFileName: File, pDestWidth: Int): File?{
            // we'll start with the original picture already open to a file
            val imgFileOrig = imageFileName //change "getPic()" for whatever you need to open the image file.
            val b = BitmapFactory.decodeFile(imgFileOrig.getAbsolutePath())
            // original measurements
            val origWidth = b.getWidth()
            val origHeight = b.getHeight()

            val destWidth = origWidth //pDestWidth//600//or the width you need

            if (origWidth >= destWidth) {
                // picture is wider than we want it, we calculate its target height
                val destHeight = origHeight / (origWidth / destWidth)
                // we create an scaled bitmap so it reduces the image, not just trim it
                val b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false)
                val outStream = ByteArrayOutputStream()
                // compress to the format you want, JPEG, PNG...
                // 70 is the 0-100 quality percentage
                b2.compress(Bitmap.CompressFormat.JPEG, 70, outStream)
                // we save the file, at least until we have made use of it
                val path = Environment.getExternalStorageDirectory()
                val f = File("$path" + "$separator" + "test.jpg")
                f.createNewFile()
                //write the bytes in file
                val fo = FileOutputStream(f)
                fo.write(outStream.toByteArray())
                // remember close de FileOutput
                fo.close()

                return f
            }

            return null
        }

        fun scaleQualityImage(sourceFile: File, destinationFile: File, maxSize: Int = 100*1000): File{
            // we'll start with the original picture already open to a file
            val bitmap = BitmapFactory.decodeFile(sourceFile.absolutePath)

            if (destinationFile.exists()) {
                destinationFile.delete()
            }

            var streamLength = maxSize
            var compressQuality = 105
            val bmpStream = ByteArrayOutputStream()
            while (streamLength >= maxSize && compressQuality > 5) {
                try {
                    bmpStream.flush()//to avoid out of memory error
                    bmpStream.reset()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                compressQuality -= 5
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
                val bmpPicByteArray = bmpStream.toByteArray()
                streamLength = bmpPicByteArray.size
            }

            val fo: FileOutputStream

            try {
                fo = FileOutputStream(destinationFile)
                fo.write(bmpStream.toByteArray())
                fo.flush()
                fo.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return destinationFile
        }
    }
}