@file:Suppress("unused")

package com.lukeneedham.flowerpotrecycler.util.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToCenter() {
    val adapter = adapter ?: return
    val centerPosition = adapter.itemCount / 2
    layoutManager?.scrollToPosition(centerPosition)
}
