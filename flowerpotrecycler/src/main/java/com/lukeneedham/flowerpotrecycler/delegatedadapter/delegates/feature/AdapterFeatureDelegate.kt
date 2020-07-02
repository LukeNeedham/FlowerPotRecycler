package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.TypedRecyclerViewHolder

/** A delegate for extended functionality on the adapter */
interface AdapterFeatureDelegate<ItemType, ItemViewType>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    /**
     * A hook for after the ViewHolder has been created in [onCreateViewHolder].
     * This hook can be used to modify the created View after creation.
     */
    fun onViewHolderCreated(
        viewHolder: TypedRecyclerViewHolder<ItemType, ItemViewType>,
        parent: ViewGroup,
        viewType: Int
    )

    /**
    A hook for after the adapter has done it's work in [onBindViewHolder]
    Warning: Do not change the onClickListener of the item view from here,
    as to do so will remove any previously set onClickListener!
    Instead, use an [OnItemClickDelegate]
     */
    fun onBindViewHolder(
        holder: TypedRecyclerViewHolder<ItemType, ItemViewType>,
        position: Int
    )

    fun onItemClick(item: ItemType, position: Int)
}