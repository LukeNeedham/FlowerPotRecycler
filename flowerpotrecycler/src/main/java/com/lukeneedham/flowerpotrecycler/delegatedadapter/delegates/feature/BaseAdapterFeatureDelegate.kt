package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/** Implements the methods of [AdapterFeatureDelegate] with NO-OP */
abstract class BaseAdapterFeatureDelegate<ItemType> : AdapterFeatureDelegate<ItemType> {

    override fun onViewHolderCreated(
        viewHolder: RecyclerView.ViewHolder,
        parent: ViewGroup,
        viewType: Int
    ) {
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
    }

    override fun onItemClick(item: ItemType, position: Int) {
    }
}
