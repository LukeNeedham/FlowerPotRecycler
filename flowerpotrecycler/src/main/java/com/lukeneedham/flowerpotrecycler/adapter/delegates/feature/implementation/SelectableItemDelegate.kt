package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

/**
 * A delegate that allows for up to 1 item to be selected at a time. Initially, no item is selected.
 * To return to the no item selected state, use [resetSelection]
 *
 * To show the selected state, [onViewSelected] will be called.
 * It defaults to calling [View.setSelected],
 * so if using the default [ItemViewType] needs to override [View.setSelected]
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class SelectableItemDelegate<ItemType, ItemViewType : View>(
    private val adapter: DelegatedRecyclerAdapter<ItemType, ItemViewType>,
    private val onSelectedPositionChangeListener: (oldPosition: Int, newPosition: Int) -> Unit = { _, _ -> },
    /**
     * The callback for each item view when its selected state changes.
     * The default is to simply call [View.setSelected].
     * Don't forget to reset the UI to the unselected state when an item is unselected.
     */
    private val onViewSelected:
        (itemView: ItemViewType, item: ItemType, isSelected: Boolean) -> Unit =
        ::defaultOnViewSelected
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION
        set(value) {
            val oldPosition = field
            field = value
            if (oldPosition != RecyclerView.NO_POSITION) {
                adapter.notifyItemChanged(oldPosition)
            }
            if (value != RecyclerView.NO_POSITION) {
                adapter.notifyItemChanged(value)
            }
            onSelectedPositionChangeListener(oldPosition, value)
        }

    override fun onViewHolderBound(
        holder: ViewHolder<ItemViewType>,
        position: Int,
        itemView: ItemViewType,
        item: ItemType
    ) {
        // Check for matching items, rather than position.
        // This allows for handling of duplicate items at different positions
        val selectedItem = getSelectedItem()
        val isSelected = selectedItem == item
        onViewSelected(itemView, item, isSelected)
    }

    override fun onItemClick(item: ItemType, position: Int, itemView: ItemViewType) {
        selectedItemPosition = position
    }

    fun getSelectedPosition(): Int = selectedItemPosition

    fun getSelectedItem(): ItemType? =
        if (selectedItemPosition == RecyclerView.NO_POSITION) {
            null
        } else {
            adapter.positionDelegate.getItemAt(selectedItemPosition)
        }

    fun selectPosition(position: Int) {
        selectedItemPosition = position
    }

    fun selectItem(item: ItemType) {
        selectedItemPosition = adapter.positionDelegate.getPositionOfItem(item)
    }

    /** Resets so that no item is selected */
    fun resetSelection() {
        selectedItemPosition = RecyclerView.NO_POSITION
    }

    companion object {
        fun <ItemType, ItemViewType : View> defaultOnViewSelected(
            itemView: ItemViewType,
            item: ItemType,
            isSelected: Boolean
        ) {
            itemView.isSelected = isSelected
        }
    }
}
