package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

/**
 * For static views. The static view will be shown for items of type [ItemType]
 * Because this is a static view, no binding is required
 */
class StaticItemBuilderBinder<ItemType : Any, ItemViewType : View>(
    override val itemTypeClass: KClass<ItemType>,
    private val viewCreator: (Context) -> ItemViewType
) : ItemBuilderBinder<ItemType, ItemViewType>() {

    override fun createView(parent: ViewGroup): ItemViewType = viewCreator(parent.context)

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        // NOOP - no binding needs to be done, because this view is static
    }

    companion object {
        fun <ItemType : Any, ItemViewType : View> fromClass(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>
        ): StaticItemBuilderBinder<ItemType, ItemViewType> =
            StaticItemBuilderBinder(itemClass) {
                viewClass.java.getConstructor(Context::class.java).newInstance(it)
            }

        inline fun <reified ItemType : Any, reified ItemViewType : View> fromType():
                StaticItemBuilderBinder<ItemType, ItemViewType> = fromClass(
            ItemType::class,
            ItemViewType::class
        )
    }
}




