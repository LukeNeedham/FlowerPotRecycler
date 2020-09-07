package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.content.Context
import android.view.View
import kotlin.reflect.KClass

interface IItemToViewType<ItemType : Any, ItemViewType : View> {
    /**
     * The class which will be checked against items when matching ItemToViewTypes
     * This is `KClass<*>`, rather than `KClass<ItemType>`, to allow for upper-bounds
     */
    val itemTypeClass: KClass<*>

    fun createView(context: Context): ItemViewType

    fun bind(holder: TypedViewHolder<ItemViewType>, position: Int, item: ItemType)
}
