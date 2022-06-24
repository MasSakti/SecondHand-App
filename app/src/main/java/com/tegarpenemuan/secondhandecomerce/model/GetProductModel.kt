package com.tegarpenemuan.secondhandecomerce.model

/**
 * com.tegarpenemuan.secondhandecomerce.model
 *
 * Created by Tegar Penemuan on 24/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

data class GetProductModel(
    val base_price: Int,
    val description: String,
    val id: Int,
    val image_name: String,
    val image_url: String,
    val location: String,
    val name: String,
    val status: String,
    val user_id: Int
)