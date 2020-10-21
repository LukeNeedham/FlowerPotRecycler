package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

/**
 * A delegate that allows for 0 or more items to be selected at a time.
 * Initially, no items are selected.
 * To return to the no item selected state, use [clearSelection].
 *
 * To show the selected state, [viewUpdater] will be called.
 * It defaults to calling [View.setSelected],
 * so your [ItemViewType] needs to override [View.setSelected].
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class MultipleSelectableItemDelegate<ItemType : Any, ItemViewType : View>(
    private val adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>,

    /**
     * The callback for each item view when its selected state changes.
     * The default is to simply call [View.setSelected].
     * Don't forget to reset the UI to the unselected state when an item is unselected.
     */
    private val viewUpdater: (itemView: ItemViewType, item: ItemType, isSelected: Boolean) -> Unit =
        ::defaultViewSelector,

    /**
     * Call for when the selected position changes.
     * Set [viewUpdater] to customise how to update the item views for this change.
     */
    private val onSelectionChangeListener:
        (oldSelectedPositions: Set<Int>, newSelectedPositions: Set<Int>) -> Unit = { _, _ -> }
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {

    private val selectedItemPositions: MutableSet<Int> = mutableSetOf()

    override fun onViewHolderBound(
        holder: ViewHolder<ItemViewType>,
        position: Int,
        itemView: ItemViewType,
        item: ItemType
    ) {
        // Check for matching items, rather than position.
        // This allows for handling of duplicate items at different positions
        val isSelected = isItemSelected(item)
        viewUpdater(itemView, item, isSelected)
    }

    override fun onItemClick(item: ItemType, position: Int, itemView: ItemViewType) {
        if (isPositionSelected(position)) {
            onPositionDeselected(position)
        } else {
            onPositionSelected(position)
        }
    }

    fun getSelectedPositions(): Set<Int> = selectedItemPositions.toSet()

    fun getSelectedItems(): Set<ItemType> = getSelectedPositions().map {
        adapter.positionDelegate.getItemAt(it)
    }.toSet()

    fun selectPosition(position: Int) {
        onPositionSelected(position)
    }

    fun selectItem(item: ItemType) {
        onPositionSelected(adapter.positionDelegate.getPositionOfItem(item))
    }

    /** Resets so that no item is selected */
    fun clearSelection() {
        val oldSelectedPositions = selectedItemPositions.toSet()
        selectedItemPositions.clear()
        oldSelectedPositions.forEach {
            adapter.notifyItemChanged(it)
        }
        onSelectionChangeListener(oldSelectedPositions, selectedItemPositions)
    }

    fun isPositionSelected(position: Int): Boolean {
        return position in selectedItemPositions
    }

    fun isItemSelected(item: ItemType): Boolean {
        return item in getSelectedItems()
    }

    private fun onPositionSelected(position: Int) {
        val oldSelectedPositions = selectedItemPositions.toSet()
        selectedItemPositions.add(position)
        adapter.notifyItemChanged(position)
        onSelectionChangeListener(oldSelectedPositions, selectedItemPositions)
    }

    private fun onPositionDeselected(position: Int) {
        val oldSelectedPositions = selectedItemPositions.toSet()
        selectedItemPositions.remove(position)
        adapter.notifyItemChanged(position)
        onSelectionChangeListener(oldSelectedPositions, selectedItemPositions)
    }

    companion object {
        fun <ItemType, ItemViewType : View> defaultViewSelector(
            itemView: ItemViewType,
            item: ItemType,
            isSelected: Boolean
        ) {
            itemView.isSelected = isSelected
        }
    }
}
