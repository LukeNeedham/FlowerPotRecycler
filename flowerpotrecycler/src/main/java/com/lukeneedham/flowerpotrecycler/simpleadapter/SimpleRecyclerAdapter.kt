package com.lukeneedham.flowerpotrecycler.simpleadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleRecyclerAdapter<ItemType, ItemViewType>(private val items: List<ItemType>) :
    RecyclerView.Adapter<SimpleRecyclerViewHolder<ItemType, ItemViewType>>()
        where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> {

    /**
     * Must be set before onCreateViewHolder is called. Otherwise, the value is ignored
     */
    var itemViewLayoutParams: RecyclerView.LayoutParams? = null

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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleRecyclerViewHolder<ItemType, ItemViewType>, position: Int) {
        val item = items[position]
        val itemView = holder.typedItemView
        itemView.setItem(position, item, itemView)
    }

    fun submitList(newItems: List<ItemType>) {
        val diffResult = DiffUtil.calculateDiff(SimpleDiffCallback(items, newItems))
        diffResult.dispatchUpdatesTo(this)
    }
}