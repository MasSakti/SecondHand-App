package id.co.binar.secondhand.model.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatchNotifResponse(
    val data: Int? = null
) : Parcelable