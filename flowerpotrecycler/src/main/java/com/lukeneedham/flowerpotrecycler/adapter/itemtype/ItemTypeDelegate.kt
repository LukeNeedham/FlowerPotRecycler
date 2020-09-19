package com.lukeneedham.flowerpotrecycler.adapter.itemtype

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.AdapterFeatureDelegate

/**
 * A TypeDel - short for TypeDelegate.
 * Responsible for a single type in a RecyclerView.
 *
 * Holds a [builderBinder] along with the [featureDelegates] to use for it.
 *
 * The workarounds to type-erasure live here.
 * The [ItemTypeRegistry] will pass in generic parameters,
 * which it has ensured are of the type expected by this [ItemTypeDelegate].
 * This gives us certainty that the unchecked-casts in this class will succeed.
 * But the compiler doesn't know that!
 */
@Suppress("UNCHECKED_CAST")
class ItemTypeDelegate<ItemType : Any, ItemViewType : View>(
    val builderBinder: BuilderBinder<ItemType, ItemViewType>,
    /**
     * Determines whether an item 'matches' this BuilderBinder,
     * and should therefore handle its building and binding.
     */
    val itemMatcher: ItemMatcher<ItemType>,
    val featureDelegates: List<AdapterFeatureDelegate<ItemType, ItemViewType>>
) {

    fun build(parent: ViewGroup): ItemViewType {
        return builderBinder.build(parent)
    }

    fun onBuilt(parent: ViewGroup, itemTypeId: Int, viewHolder: ViewHolder<*>) {
        val typedHolder = requireTypedViewHolder(viewHolder)
        featureDelegates.forEach {
            it.onViewHolderCreated(parent, itemTypeId, typedHolder, typedHolder.typedItemView)
        }
    }

    fun bindUntyped(viewHolder: ViewHolder<*>, position: Int, item: Any) {
        val typedHolder = requireTypedViewHolder(viewHolder)
        val typedItem = requireTypedItem(item)
        val view = typedHolder.typedItemView
        builderBinder.bind(view, position, typedItem)

        featureDelegates.forEach {
            it.onViewHolderBound(typedHolder, position, view, typedItem)
        }
        // Called after onViewHolderBound to override any click listener set on itemView incorrectly
        // during onViewHolderBound. For compatibility, all click handling must go through onItemClick
        view.setOnClickListener {
            featureDelegates.forEach {
                it.onItemClick(typedItem, position, view)
            }
        }
    }

    fun matchesItem(item: Any): Boolean {
        return itemMatcher.isMatch(item)
    }

    private fun requireTypedViewHolder(viewHolder: ViewHolder<*>): ViewHolder<ItemViewType> {
        return viewHolder as? ViewHolder<ItemViewType>
            ?: throw FlowerPotRecyclerException("ViewHolder $viewHolder is of the wrong type for $this")
    }

    private fun requireTypedItem(item: Any): ItemType {
        return item as? ItemType
            ?: throw FlowerPotRecyclerException("Item $item is of the wrong type for $this")
    }
}
