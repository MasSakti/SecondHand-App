package com.tegarpenemuan.secondhandecomerce.data.api.updateUser

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

/**
 * com.tegarpenemuan.secondhandecomerce.data.api.updateUser
 *
 * Created by Tegar Penemuan on 25/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

data class UpdateUserRequest(
    @Part("full_name") var full_name: RequestBody? = null,
    @Part("email") var email: RequestBody? = null,
    @Part("password") var password: RequestBody? = null,
    @Part("phone_number") var phone_number: RequestBody? = null,
    @Part("address") var address: RequestBody? = null,
    @Part var image: MultipartBody.Part? = null,
    @Part("city") var city: RequestBody? = null
)