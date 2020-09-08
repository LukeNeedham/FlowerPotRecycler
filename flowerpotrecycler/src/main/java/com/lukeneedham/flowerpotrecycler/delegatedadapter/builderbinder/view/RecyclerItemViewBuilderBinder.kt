package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemTypeMatcher
import com.lukeneedham.flowerpotrecycler.util.build
import kotlin.reflect.KClass

/** Builds the item view with [viewCreator] and implicitly binds using [RecyclerItemView.setItem] */
class RecyclerItemViewBuilderBinder<ItemType : Any, ItemViewType>(
    override val itemMatcher: ItemTypeMatcher<ItemType>,
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
            RecyclerItemViewBuilderBinder(ClassMatcher(itemClass)) {
                viewClass.build(it.context)
            }

        inline fun <reified ItemType : Any, reified ItemViewType> fromItemType():
                RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            fromItemClass(
                ItemType::class,
                ItemViewType::class
            )
    }
}
