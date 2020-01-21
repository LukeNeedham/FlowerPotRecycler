package com.lukeneedham.flowerpotrecycler.simpleadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate.AdapterPositionDelegate
import com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate.CyclicPositionDelegate
import com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate.LinearPositionDelegate

abstract class SimpleRecyclerAdapter<ItemType, ItemViewType>(items: List<ItemType> = emptyList()) :
    RecyclerView.Adapter<SimpleRecyclerViewHolder<ItemType, ItemViewType>>()
        where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> {
            
    protected val defaultDiffCallback = object : DiffUtil.ItemCallback<ItemType>() {
            override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType) =
                oldItem == newItem
        }

    var positionDelegate: AdapterPositionDelegate<ItemType> =
        LinearPositionDelegate(this, defaultDiffCallback).apply {
            submitList(items)
        }

    /**
     * Must be set before onCreateViewHolder is called. Otherwise, the value is ignored
     */
    var itemViewLayoutParams: RecyclerView.LayoutParams? = null

    /**
     * Set to true if the items of the recyclerview should 'wrap-around' -
     * so the item after the last item in your list is the first item again.
     */
    var isCyclic: Boolean = false
        set(value) {
            field = value
            val items = positionDelegate.getItems()
            positionDelegate =
                if (value) CyclicPositionDelegate(this, defaultDiffCallback) else LinearPositionDelegate(this, defaultDiffCallback)
        }

    abstract fun createItemView(context: Context): ItemViewType

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleRecyclerViewHolder<ItemType, ItemViewType> {
        val view = createItemView(parent.context)
        if (itemViewLayoutParams != null) {
            view.layoutParams = RecyclerView.LayoutParams(itemViewLayoutParams)
        }
        return SimpleRecyclerViewHolder(view)
    }

    override fun getItemCount() = positionDelegate.getItemCount()

    override fun onBindViewHolder(
        holder: SimpleRecyclerViewHolder<ItemType, ItemViewType>,
        position: Int
    ) {
        val item = positionDelegate.getItemAt(position)
        val itemView = holder.typedItemView
        itemView.setItem(position, item)
    }

    fun submitList(newItems: List<ItemType>, onDiffDone: () -> Unit = {}) {
        positionDelegate.submitList(newItems, onDiffDone)
    }
}
