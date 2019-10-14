package com.lukeneedham.flowerpotrecycler.reflectionadapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.simpleadapter.SimpleRecyclerAdapter
import com.lukeneedham.flowerpotrecycler.simpleadapter.SimpleRecyclerItemView
import com.lukeneedham.flowerpotrecycler.simpleadapter.SimpleRecyclerViewHolder

object ReflectionRecyclerAdapter {

    inline fun <ItemType, reified ItemViewType> create(items: List<ItemType>)
            : RecyclerView.Adapter<SimpleRecyclerViewHolder<ItemType, ItemViewType>>
            where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType> =
        object : SimpleRecyclerAdapter<ItemType, ItemViewType>(items) {
            override fun createItemView(context: Context) =
                ItemViewType::class.java.getConstructor(Context::class.java).newInstance(context)
        }
}