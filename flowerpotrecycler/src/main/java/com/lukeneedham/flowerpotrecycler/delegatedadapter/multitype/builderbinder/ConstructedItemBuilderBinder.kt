package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.builderbinder

import android.content.Context
import android.view.View
import kotlin.reflect.KClass

class ConstructedItemBuilderBinder<ItemType : Any, ItemViewType : View>(
    override val itemTypeClass: KClass<ItemType>,
    override val itemViewTypeClass: KClass<ItemViewType>,
    private val viewCreator: (Context) -> ItemViewType,
    private val itemBinder: (position: Int, item: ItemType, itemView: ItemViewType) -> Unit
) : ItemBuilderBinder<ItemType, ItemViewType>() {

    override fun createView(context: Context): ItemViewType = viewCreator(context)

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemBinder(position, item, itemView)
    }
}
