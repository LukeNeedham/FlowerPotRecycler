package com.lukeneedham.flowerpotrecyclersample.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FlowerPotModel(@StringRes val nameResId: Int, @DrawableRes val imageResId: Int)
