package id.co.binar.secondhand.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


fun Context.buildImageMultipart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
    val leftImageFile = this.convertBitmapToFile(fileName, bitmap)
    val reqFile = leftImageFile.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
}

fun Context.convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
    //create a file to write bitmap data
    val file = File(this.cacheDir, fileName)
    file.createNewFile()

    //Convert bitmap to byte array
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
    val bitMapData = bos.toByteArray()

    //write the bytes in file
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    try {
        fos?.write(bitMapData)
        fos?.flush()
        fos?.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file
}

fun convertBitmapLocalToByteArray(bitmap: Bitmap): ByteArray {
    val bStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
    return bStream.toByteArray()
}

fun convertFileLocalToBitmap(byteArray: ByteArray): Bitmap? {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}