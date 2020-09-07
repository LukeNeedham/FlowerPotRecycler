package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position

import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DefaultDiffCallback
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position.implementation.LinearPositionDelegate

object DefaultPositionDelegate {
    fun <ItemType> create(adapter: RecyclerView.Adapter<*>) =
        LinearPositionDelegate<ItemType>(adapter, DefaultDiffCallback())
}
