package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.createBuilder
import kotlin.reflect.KClass

/** Builds the item view with [viewCreator] and implicitly binds using [RecyclerItemView.setItem] */
class RecyclerItemViewBuilderBinder<ItemType : Any, ItemViewType>(
    override val itemMatcher: ItemMatcher<ItemType>,
    private val viewCreator: Builder<ItemViewType>
) : BuilderBinder<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun build(parent: ViewGroup): ItemViewType {
        val view = viewCreator(parent)
        // TODO: Generate layout params based on parent, like when inflating from XML
        return view
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemView.setItem(position, item)
    }

    companion object {
        fun <ItemType : Any, ItemViewType> fromItemClass(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>
        ): RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            RecyclerItemViewBuilderBinder(ClassMatcher(itemClass), viewClass.createBuilder())

        inline fun <reified ItemType : Any, reified ItemViewType> fromItemType():
                RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            fromItemClass(ItemType::class, ItemViewType::class)
    }
}
