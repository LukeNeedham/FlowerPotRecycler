package com.lukeneedham.flowerpotrecycler.simpleadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleRecyclerAdapter<ItemType, ItemViewType>(private val items: List<ItemType>) :
    RecyclerView.Adapter<SimpleRecyclerViewHolder<ItemType, ItemViewType>>()
        where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> {

    abstract fun createItemView(context: Context): ItemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SimpleRecyclerViewHolder(createItemView(parent.context))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleRecyclerViewHolder<ItemType, ItemViewType>, position: Int) {
        val item = items[position]
        val itemView = holder.typedItemView
        itemView.setItem(item, itemView)
    }
}