package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

/**
 * A delegate that allows for up to 1 item to be selected at a time. Initially, no item is selected.
 * To return to the no item selected state, use [resetSelection]
 *
 * To show the selected state, your ItemViewType needs to override [View.setSelected]
 */
@Suppress("unused")
class SelectableItemDelegate<ItemType : Any>(
    private val adapter: DelegatedRecyclerAdapter<ItemType>,
    private val onSelectedPositionChangeListener: (oldPosition: Int, newPosition: Int) -> Unit = { _, _ -> },
    /**
     * The callback for each item view when its selected state changes.
     * The default is to simply call [View.setSelected].
     * Don't forget to reset the UI to the unselected state when [isSelected] is false.
     */
    private val onViewSelected: (itemView: View, isSelected: Boolean, item: ItemType) -> Unit =
        ::defaultOnViewSelected
) : BaseAdapterFeatureDelegate<ItemType>() {

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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = adapter.positionDelegate.getItemAt(position)
        val itemView = holder.itemView
        // Check for matching items, rather than position.
        // This allows for handling of duplicate items at different positions
        val selectedItem = if (selectedItemPosition == RecyclerView.NO_POSITION) {
            null
        } else {
            getSelectedItem()
        }
        onViewSelected(itemView, selectedItem == item, item)
    }

    override fun onItemClick(item: ItemType, position: Int) {
        selectedItemPosition = position
    }

    fun getSelectedPosition() = selectedItemPosition

    fun getSelectedItem() = adapter.positionDelegate.getItemAt(selectedItemPosition)

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
        fun <ItemType : Any> defaultOnViewSelected(
            itemView: View,
            isSelected: Boolean,
            item: ItemType
        ) {
            itemView.isSelected = isSelected
        }
    }
}
