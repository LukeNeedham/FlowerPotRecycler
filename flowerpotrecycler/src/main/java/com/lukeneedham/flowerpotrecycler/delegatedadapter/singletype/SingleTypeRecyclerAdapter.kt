package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.builderbinder.RecyclerItemViewBuilderBinder
import kotlin.reflect.KClass

/**
 * A base RecyclerView Adapter to encourage a delegated approach.
 * It handles a single type of item and view
 */
abstract class SingleTypeRecyclerAdapter<ItemType : Any, ItemViewType> :
    DelegatedRecyclerAdapter<ItemType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    abstract val itemTypeClass: KClass<ItemType>
    abstract val itemViewTypeClass: KClass<ItemViewType>

    override val builderBinderRegistry: BuilderBinderRegistry<ItemType> by lazy {
        BuilderBinderRegistry(
            listOf(
                RecyclerItemViewBuilderBinder(
                    itemTypeClass,
                    itemViewTypeClass,
                    ::createItemView
                )
            )
        )
    }

    protected abstract fun createItemView(context: Context): ItemViewType
}
