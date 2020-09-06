package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.MultiTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.TypeToView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.ViewTypesDelegate
import kotlin.reflect.KClass

/**
 * A base RecyclerView Adapter to encourage a delegated approach.
 * It handles a single type of item and view
 */
abstract class SingleTypeRecyclerAdapter<ItemType : Any, ItemViewType>() :
    MultiTypeRecyclerAdapter<ItemType, ItemViewType, TypedRecyclerViewHolder<ItemType, ItemViewType>>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    abstract val itemClass: KClass<ItemType>

    override val viewTypeDelegate: ViewTypesDelegate<ItemType, ItemViewType> by lazy {
        ViewTypesDelegate(listOf(TypeToView(itemClass, ::createItemView, ::bindItemView)))
    }

    protected abstract fun createItemView(context: Context): ItemViewType

    override fun createViewHolder(view: ItemViewType) = TypedRecyclerViewHolder(view)

    private fun bindItemView(position: Int, item: ItemType, itemView: ItemViewType) {
        itemView.setItem(position, item)
    }
}
