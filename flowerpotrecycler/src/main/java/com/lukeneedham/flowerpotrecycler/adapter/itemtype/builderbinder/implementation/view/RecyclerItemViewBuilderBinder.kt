package com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createReflectiveBuilder

/** Builds the item view with [builder] and implicitly binds using [RecyclerItemView.setItem] */
class RecyclerItemViewBuilderBinder<ItemType : Any, ItemViewType>(
    private val builder: Builder<ItemViewType>
) : BuilderBinder<ItemType, ItemViewType>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun build(parent: ViewGroup): ItemViewType {
        return builder(parent)
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemView.setItem(position, item)
    }

    companion object {
        inline fun <reified ItemType : Any, reified ItemViewType> newInstance(
            noinline builder: Builder<ItemViewType> = createReflectiveBuilder()
        ): RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            RecyclerItemViewBuilderBinder(builder)
    }
}
