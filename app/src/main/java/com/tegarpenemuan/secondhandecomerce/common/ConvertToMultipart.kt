package com.tegarpenemuan.secondhandecomerce.common

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * com.tegarpenemuan.secondhandecomerce.common
 *
 * Created by Tegar Penemuan on 14/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

object ConvertToMultipart {
    fun File?.toMultipartBody(name: String = "image"): MultipartBody.Part? {
        if (this == null) return null
        val reqFile: RequestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, this.name, reqFile)
    }
}