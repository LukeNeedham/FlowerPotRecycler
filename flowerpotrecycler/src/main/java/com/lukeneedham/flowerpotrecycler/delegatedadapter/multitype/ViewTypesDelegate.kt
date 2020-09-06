package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.content.Context
import android.view.View

class ViewTypesDelegate<BaseType : Any, BaseViewType : View>(private val itemTypeToViews: List<TypeToView<BaseType, BaseViewType>>) {
    fun createView(context: Context, itemTypeId: Int): BaseViewType {
        val typeToView = itemTypeToViews.first { it.typeId == itemTypeId }
        return typeToView.createView(context)
    }

    fun bind(holder: TypedViewHolder<BaseViewType>, position: Int, item: BaseType) {
        val typeToView = itemTypeToViews.first { it.matchesItemType(item) }
        val itemView = holder.typedItemView
        typeToView.bind(itemView, position, item)
    }
}
