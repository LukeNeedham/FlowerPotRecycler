package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView

class RecyclerItemToViewTypeImpl<ItemType : Any, ItemViewType>(
    private val viewCreator: (Context) -> ItemViewType
) :
    RecyclerItemToViewType<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun createView(context: Context): ItemViewType {
        return viewCreator(context)
    }

    companion object {
        fun <ItemType : Any, ItemViewType> fromClass(viewClass: Class<ItemViewType>):
                RecyclerItemToViewTypeImpl<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            RecyclerItemToViewTypeImpl {
                viewClass.getConstructor(Context::class.java).newInstance(it)
            }

        inline fun <ItemType : Any, reified ItemViewType> fromType():
                RecyclerItemToViewTypeImpl<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            fromClass(ItemViewType::class.java)
    }
}
