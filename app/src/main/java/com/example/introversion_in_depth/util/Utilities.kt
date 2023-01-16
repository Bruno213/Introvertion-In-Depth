package com.example.introversion_in_depth.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveImage(file: File, context: Context, image: Bitmap): Uri? {
    val uri: Uri? = try {
        file.mkdirs()
        val fileImage = File(file, "shared_image.png")
        val stream = FileOutputStream(fileImage)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()

        FileProvider.getUriForFile(context, "com.com.introversion_in_depth.fileprovider", fileImage)
    } catch (e: IOException) {
        Log.d(ContentValues.TAG, "IOException while trying to write file for sharing: " + e.message)
        null
    }
    return uri
}


fun screenShot(view: View): Bitmap {
    val bitmap: Bitmap = Bitmap.createBitmap(
        view.width,
        view.height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
}

fun shareImageUri(context: Context, uri: Uri) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "image/png"
    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
    context.startActivity(Intent.createChooser(sharingIntent, "Share File"))
}