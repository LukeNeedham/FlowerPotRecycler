package com.lukeneedham.flowerpotrecycler.reflectionadapter

import android.content.Context
import android.view.View
import com.lukeneedham.flowerpotrecycler.simpleadapter.SimpleRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.simpleadapter.SimpleRecyclerItemView

object ReflectionRecyclerAdapter {

    @JvmStatic
    fun <ItemType, ItemViewType> create(items: List<ItemType>, itemViewClass: Class<ItemViewType>)
            where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> =
        object : SimpleRecyclerAdapter<ItemType, ItemViewType>(items) {
            override fun createItemView(context: Context) =
                itemViewClass.getConstructor(Context::class.java).newInstance(context)
        }
}