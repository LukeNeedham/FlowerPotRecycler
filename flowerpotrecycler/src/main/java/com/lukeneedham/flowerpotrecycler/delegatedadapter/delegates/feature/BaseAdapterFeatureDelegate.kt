package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature

import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.TypedRecyclerViewHolder

/** Implements the methods of [AdapterFeatureDelegate] with NO-OP */
abstract class BaseAdapterFeatureDelegate<ItemType> : AdapterFeatureDelegate<ItemType> {

    override fun onViewHolderCreated(
        viewHolder: TypedRecyclerViewHolder<ItemType, *>,
        parent: ViewGroup,
        viewType: Int
    ) {
    }

    override fun onBindViewHolder(
        holder: TypedRecyclerViewHolder<ItemType, *>,
        position: Int
    ) {
    }

    override fun onItemClick(item: ItemType, position: Int) {
    }
}