package com.tegarpenemuan.secondhandecomerce.ui.onboarding

import android.media.Image
import androidx.annotation.DrawableRes

data class OnBoardingData(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)
