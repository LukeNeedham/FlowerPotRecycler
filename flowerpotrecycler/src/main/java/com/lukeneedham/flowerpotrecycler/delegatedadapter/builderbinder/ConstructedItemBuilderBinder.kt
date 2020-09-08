package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

class ConstructedItemBuilderBinder<ItemType : Any, ItemViewType : View>(
    override val itemTypeClass: KClass<ItemType>,
    private val viewCreator: (Context) -> ItemViewType,
    private val itemBinder: (position: Int, item: ItemType, itemView: ItemViewType) -> Unit
) : ItemBuilderBinder<ItemType, ItemViewType>() {

    override fun createView(parent: ViewGroup): ItemViewType {
        // TODO: Generate layout params based on parent, like when inflating from XML
        return viewCreator(parent.context)
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemBinder(position, item, itemView)
    }
}
