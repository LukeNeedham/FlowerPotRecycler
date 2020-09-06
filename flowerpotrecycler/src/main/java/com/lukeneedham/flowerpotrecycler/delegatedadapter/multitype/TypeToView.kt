package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.BuilderBinder
import kotlin.reflect.KClass

class TypeToView<ItemType : Any, ItemViewType : View>(
    typeClass: KClass<ItemType>,
    private val viewCreator: (Context) -> ItemViewType,
    private val itemBinder: (position: Int, item: ItemType, itemView: ItemViewType) -> Unit
) {
    val typeId: Int = typeClass.getTypeId()

    fun createView(context: Context): ItemViewType = viewCreator(context)

    fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemBinder(position, item, itemView)
    }

    fun matchesItemType(item: Any): Boolean {
        return typeId == item::class.getTypeId()
    }

    companion object {
        private fun KClass<*>.getTypeId(): Int = hashCode()
    }
}
