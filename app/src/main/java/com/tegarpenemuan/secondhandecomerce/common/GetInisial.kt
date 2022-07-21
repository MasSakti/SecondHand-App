package com.tegarpenemuan.secondhandecomerce.common

/**
 * com.tegarpenemuan.secondhandecomerce.common
 *
 * Created by Tegar Penemuan on 21/07/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

object GetInisial {
    fun String?.getInitial(): String {
        if (this.isNullOrEmpty()) return ""
        val array = this.split(" ")
        if (array.isEmpty()) return this
        var inisial = array[0].substring(0, 1)
        if (array.size > 1) inisial += array[1].substring(0, 1)
        return inisial.uppercase()
    }
}