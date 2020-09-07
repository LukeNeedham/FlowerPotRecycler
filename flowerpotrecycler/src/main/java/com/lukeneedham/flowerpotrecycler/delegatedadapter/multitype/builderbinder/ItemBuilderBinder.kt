package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype.builderbinder

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException
import kotlin.reflect.KClass

/** Handles the building of the view, and the binding of the item for each item type */
abstract class ItemBuilderBinder<ItemType : Any, ItemViewType : View> {
    /**
     * The class which will be checked against items when matching ItemToViewTypes
     */
    abstract val itemTypeClass: KClass<ItemType>

    abstract val itemViewTypeClass: KClass<ItemViewType>

    abstract fun createView(context: Context): ItemViewType

    abstract fun bind(itemView: ItemViewType, position: Int, item: ItemType)

    /* This is a nasty workaround to type-erasure */
    fun bindUntyped(view: View, position: Int, item: Any) {
        val typedView = itemViewTypeClass.java.cast(view)
            ?: throw FlowerPotRecyclerException("View $view must be of type $itemViewTypeClass to bind with $this")
        val typedItem = itemTypeClass.java.cast(item)
            ?: throw FlowerPotRecyclerException("Item $item must be of type $itemTypeClass to bind with $this")
        bind(typedView, position, typedItem)
    }
}
