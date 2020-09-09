package com.lukeneedham.flowerpotrecycler.adapter.delegates.position

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation.LinearPositionDelegate

object DefaultPositionDelegate {
    fun <ItemType> create(adapter: RecyclerView.Adapter<*>) =
        LinearPositionDelegate<ItemType>(adapter, DefaultDiffCallback())
}
