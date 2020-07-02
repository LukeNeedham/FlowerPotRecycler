package com.lukeneedham.flowerpotrecycler.extensions

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToCenter() {
    val adapter = adapter ?: return
    val centerPosition = adapter.itemCount / 2
    layoutManager?.scrollToPosition(centerPosition)
}