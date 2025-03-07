package com.example.opendatajabar.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun getPathFromUri(context: Context, uri: Uri): String? {
    val fileName = getFileName(context, uri) ?: return null
    val file = File(context.cacheDir, fileName)
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    return file.absolutePath
}

private fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            fileName = if (columnIndex != -1) it.getString(columnIndex) else null
        }
    }
    return fileName
}
