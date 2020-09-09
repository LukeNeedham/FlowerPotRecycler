package com.lukeneedham.flowerpotrecycler.adapter.builderbinder

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ItemMatcher

/** Handles the building of the view, and the binding of the item for each item type */
abstract class BuilderBinder<ItemType : Any, ItemViewType : View> {

    /**
     * Determines whether an item 'matches' this BuilderBinder,
     * and should therefore handle its building and binding
     */
    abstract val itemMatcher: ItemMatcher<ItemType>

    /** @return the view used to display items of type [ItemType] */
    abstract fun build(parent: ViewGroup): ItemViewType

    /** Performs the logic for binding an item of type [ItemType] to the view built by [build] */
    abstract fun bind(itemView: ItemViewType, position: Int, item: ItemType)

    fun matchesItem(item: Any): Boolean {
        return itemMatcher.isMatch(item)
    }

    /** This is a nasty workaround to type-erasure. Prefer to use [bind] wherever possible. */
    @Suppress("UNCHECKED_CAST")
    fun bindUntyped(view: View, position: Int, item: Any) {
        val typedView = view as? ItemViewType
            ?: throw FlowerPotRecyclerException(
                "View $view is of the wrong view type for BuilderBinder ($this) registered for item type $itemMatcher"
            )
        val typedItem = item as? ItemType
            ?: throw FlowerPotRecyclerException("Item $item must be of type $itemMatcher to bind with $this")
        bind(typedView, position, typedItem)
    }
}
