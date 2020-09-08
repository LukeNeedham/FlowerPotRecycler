package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import kotlin.reflect.KClass

class RecyclerItemViewBuilderBinder<ItemType : Any, ItemViewType>(
    override val itemTypeClass: KClass<ItemType>,
    private val viewCreator: (Context) -> ItemViewType
) : ItemBuilderBinder<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun createView(parent: ViewGroup): ItemViewType {
        val view = viewCreator(parent.context)
        // TODO: Generate layout params based on parent, like when inflating from XML
        return view
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        itemView.setItem(position, item)
    }

    companion object {
        fun <ItemType : Any, ItemViewType> fromClass(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>
        ): RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            RecyclerItemViewBuilderBinder(
                itemClass
            ) {
                viewClass.java.getConstructor(Context::class.java).newInstance(it)
            }

        inline fun <reified ItemType : Any, reified ItemViewType> fromType():
                RecyclerItemViewBuilderBinder<ItemType, ItemViewType>
                where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> =
            fromClass(
                ItemType::class,
                ItemViewType::class
            )
    }
}
