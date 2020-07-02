package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.TypedRecyclerViewHolder

/** Implements the methods of [AdapterFeatureDelegate] with NO-OP */
abstract class BaseAdapterFeatureDelegate<ItemType, ItemViewType> :
    AdapterFeatureDelegate<ItemType, ItemViewType>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun onViewHolderCreated(
        viewHolder: TypedRecyclerViewHolder<ItemType, ItemViewType>,
        parent: ViewGroup,
        viewType: Int
    ) {
    }

    override fun onBindViewHolder(
        holder: TypedRecyclerViewHolder<ItemType, ItemViewType>,
        position: Int
    ) {
    }

    override fun onItemClick(item: ItemType, position: Int) {
    }
}