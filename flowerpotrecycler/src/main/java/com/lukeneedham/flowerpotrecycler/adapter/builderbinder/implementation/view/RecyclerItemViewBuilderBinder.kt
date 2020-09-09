package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.adapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createBuilder

/** Builds the item view with [builder] and implicitly binds using [RecyclerItemView.setItem] */
class RecyclerItemViewBuilderBinder<ItemType : Any, ItemViewType>(
    override val itemMatcher: ItemMatcher<ItemType>,
    private val builder: Builder<ItemViewType>
) : BuilderBinder<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun build(parent: ViewGroup): ItemViewType {
        val view = builder(parent)
        // TODO: Generate layout params based on parent, like when inflating from XML
        return view
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemView.setItem(position, item)
    }

    companion object {
        inline fun <reified ItemType : Any, reified ItemViewType> create(
            matcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
            noinline builder: Builder<ItemViewType> = createBuilder()
        ): RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            RecyclerItemViewBuilderBinder(matcher, builder)
    }
}
