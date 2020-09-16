package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder

/** A delegate for extended functionality on the adapter */
interface AdapterFeatureDelegate<ItemType : Any, ItemViewType : View> {

    /**
     * A hook for after the ViewHolder has been created in [RecyclerView.Adapter.onCreateViewHolder].
     * This hook can be used to modify the created View after creation.
     */
    fun onViewHolderCreated(
        parent: ViewGroup,
        viewType: Int,
        viewHolder: ViewHolder<ItemViewType>,
        itemView: ItemViewType
    )

    /**
     * A hook for after the adapter has done it's work in [RecyclerView.Adapter.onBindViewHolder].
     *
     * Warning: Do not change the onClickListener of the item view from here,
     * as it will be overridden by [onItemClick]. Instead, use [onItemClick] directly.
     */
    fun onViewHolderBound(
        holder: ViewHolder<ItemViewType>,
        position: Int,
        itemView: ItemViewType,
        item: ItemType
    )

    /**
     * A hook for when an item view is clicked
     */
    fun onItemClick(item: ItemType, position: Int, itemView: ItemViewType)
}
