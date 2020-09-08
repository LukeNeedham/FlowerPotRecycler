package com.lukeneedham.flowerpotrecycler.delegatedadapter.singletype

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.DelegatedRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.AllMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinderRegistry
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.view.ViewBuilderBinder

abstract class SingleTypeAdapter<ItemType : Any, ItemViewType : View> :
    DelegatedRecyclerAdapter<ItemType>() {

    final override val builderBinderRegistry: BuilderBinderRegistry<ItemType> =
        BuilderBinderRegistry(
            listOf(
                ViewBuilderBinder(AllMatcher(), ::createItemView, ::bindItemView)
            )
        )

    protected abstract fun createItemView(parent: ViewGroup): ItemViewType

    protected abstract fun bindItemView(
        itemView: ItemViewType,
        position: Int,
        item: ItemType
    )
}
