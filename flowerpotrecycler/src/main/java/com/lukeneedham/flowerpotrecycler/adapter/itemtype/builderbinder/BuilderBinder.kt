package com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder

import android.view.View
import android.view.ViewGroup

/** Handles the building of the view, and the binding of the item for each item type */
interface BuilderBinder<ItemType : Any, ItemViewType : View> {

    /** @return the view used to display items of type [ItemType] */
    fun build(parent: ViewGroup): ItemViewType

    /** Performs the logic for binding an item of type [ItemType] to the view built by [build] */
    fun bind(itemView: ItemViewType, position: Int, item: ItemType)
}
