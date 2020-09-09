package com.lukeneedham.flowerpotrecyclersample.ui.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(textRes: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), textRes, length).show()
}

fun Fragment.showSnackbar(text: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), text, length).show()
}
