package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.content.Context
import android.view.View
import kotlin.reflect.KClass

open class ItemToViewTypeImpl<ItemType : Any, ItemViewType : View>(
    override val itemTypeClass: KClass<*>,
    private val viewCreator: (Context) -> ItemViewType,
    private val itemBinder: (position: Int, item: ItemType, itemView: ItemViewType) -> Unit
) : IItemToViewType<ItemType, ItemViewType> {

    override fun createView(context: Context): ItemViewType = viewCreator(context)

    override fun bind(holder: TypedViewHolder<ItemViewType>, position: Int, item: ItemType) {
        val itemView = holder.typedItemView
        itemBinder(position, item, itemView)
    }
}
