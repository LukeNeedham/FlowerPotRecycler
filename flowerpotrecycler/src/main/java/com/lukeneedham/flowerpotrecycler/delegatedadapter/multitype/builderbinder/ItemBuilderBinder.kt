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

    abstract fun createView(context: Context): ItemViewType

    abstract fun bind(itemView: ItemViewType, position: Int, item: ItemType)

    /* This is a nasty workaround to type-erasure */
    @Suppress("UNCHECKED_CAST")
    fun bindUntyped(view: View, position: Int, item: Any) {
        val typedView = view as? ItemViewType
            ?: throw FlowerPotRecyclerException(
                "View $view is of the wrong view type for BuilderBinder ($this) registered for item type $itemTypeClass"
            )
        val typedItem = itemTypeClass.java.cast(item)
            ?: throw FlowerPotRecyclerException("Item $item must be of type $itemTypeClass to bind with $this")
        bind(typedView, position, typedItem)
    }
}
