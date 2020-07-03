package com.lukeneedham.flowerpotrecycler.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToCenter() {
    val adapter = adapter ?: return
    val centerPosition = adapter.itemCount / 2
    layoutManager?.scrollToPosition(centerPosition)
}