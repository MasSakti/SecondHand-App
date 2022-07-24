package binar.and3.kelompok1.secondhand.data.api.seller

import com.google.gson.annotations.SerializedName

data class PatchStatusRequest(
    @SerializedName("status") val status: String
)
