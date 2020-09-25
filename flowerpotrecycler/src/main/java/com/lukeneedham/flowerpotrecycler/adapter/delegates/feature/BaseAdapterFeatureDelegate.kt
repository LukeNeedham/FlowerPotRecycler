package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder

/** Implements the methods of [AdapterFeatureDelegate] with NO-OP */
abstract class BaseAdapterFeatureDelegate<ItemType, ItemViewType : View> :
    AdapterFeatureDelegate<ItemType, ItemViewType> {

    override fun onViewHolderCreated(
        parent: ViewGroup,
        viewType: Int,
        viewHolder: ViewHolder<ItemViewType>,
        itemView: ItemViewType
    ) {
    }

    override fun onViewHolderBound(
        holder: ViewHolder<ItemViewType>,
        position: Int,
        itemView: ItemViewType,
        item: ItemType
    ) {
    }

    override fun onItemClick(item: ItemType, position: Int, itemView: ItemViewType) {
    }
}
