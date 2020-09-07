package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.MultiTypeRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.RecyclerItemToViewTypeImpl
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.ViewTypesRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.ViewTypesRegistryImpl

/**
 * A base RecyclerView Adapter to encourage a delegated approach.
 * It handles a single type of item and view
 */
abstract class SingleTypeRecyclerAdapter<ItemType : Any, ItemViewType>() :
    MultiTypeRecyclerAdapter<ItemType, ItemViewType, SingleTypedViewHolder<ItemType, ItemViewType>>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override val viewTypesRegistry: ViewTypesRegistry<ItemType, ItemViewType> =
        ViewTypesRegistryImpl(listOf(RecyclerItemToViewTypeImpl(::createItemView)))

    protected abstract fun createItemView(context: Context): ItemViewType

    override fun createViewHolder(view: View): SingleTypedViewHolder<ItemType, ItemViewType> =
        SingleTypedViewHolder(view)
}
