package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import kotlin.reflect.KClass

abstract class RecyclerItemToViewType<ItemType : Any, ItemViewType> :
    IItemToViewType<ItemType, ItemViewType>
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    /*
     * By declaring this with only one [ItemToViewType], with class [Any],
     * we are declaring that all items should be handled with the same view type.
     */
    override val itemTypeClass: KClass<*> = Any::class

    override fun bind(holder: TypedViewHolder<ItemViewType>, position: Int, item: ItemType) {
        val itemView = holder.typedItemView
        itemView.setItem(position, item)
    }
}

