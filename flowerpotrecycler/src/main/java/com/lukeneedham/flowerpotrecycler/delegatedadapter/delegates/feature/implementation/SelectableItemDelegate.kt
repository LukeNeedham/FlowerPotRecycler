package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.SingleTypeRecyclerAdapter

/**
 * A delegate that allows for up to 1 item to be selected at a time. Initially, no item is selected.
 * To return to the no item selected state, use [resetSelection]
 *
 * To show the selected state, the ItemViewType of your [SingleTypeRecyclerAdapter] needs to override [View.setSelected]
 */
class SelectableItemDelegate<ItemType : Any>(
    private val adapter: DelegatedRecyclerAdapter<ItemType>,
    private val onSelectedPositionChangeListener: (oldPosition: Int, newPosition: Int) -> Unit = { _, _ -> }
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
        itemView.isSelected = selectedItem == item
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
}
