package com.lukeneedham.flowerpotrecyclersample

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FlowerPotModel(@StringRes val nameResId: Int, @DrawableRes val imageResId: Int)