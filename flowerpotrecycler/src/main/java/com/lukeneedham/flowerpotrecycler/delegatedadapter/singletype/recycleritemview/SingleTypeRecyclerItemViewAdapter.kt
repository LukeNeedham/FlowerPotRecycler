package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype.recycleritemview

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.AllMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.view.RecyclerItemViewBuilderBinder

abstract class SingleTypeRecyclerItemViewAdapter<ItemType : Any, ItemViewType> :
    DelegatedRecyclerAdapter<ItemType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    final override val builderBinderRegistry: BuilderBinderRegistry<ItemType> by lazy {
        BuilderBinderRegistry(
            listOf(RecyclerItemViewBuilderBinder(AllMatcher(), ::createItemView))
        )
    }

    protected abstract fun createItemView(parent: ViewGroup): ItemViewType
}
