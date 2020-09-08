package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.build
import kotlin.reflect.KClass

/**
 * For static views. The static view will be shown for items of type [ItemType]
 * Because this is a static view, no binding is required
 */
class StaticViewBuilderBinder<ItemType : Any, ItemViewType : View>(
    override val itemMatcher: ItemMatcher<ItemType>,
    private val builder: Builder<ItemViewType>
) : BuilderBinder<ItemType, ItemViewType>() {

    override fun build(parent: ViewGroup): ItemViewType {
        return builder(parent)
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        // NOOP - no binding needs to be done, because this view is static
    }

    companion object {
        fun <ItemType : Any, ItemViewType : View> fromClass(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>
        ): StaticViewBuilderBinder<ItemType, ItemViewType> =
            StaticViewBuilderBinder(ClassMatcher(itemClass)) {
                viewClass.build(it.context)
            }

        inline fun <reified ItemType : Any, reified ItemViewType : View> fromType():
                StaticViewBuilderBinder<ItemType, ItemViewType> =
            fromClass(
                ItemType::class,
                ItemViewType::class
            )
    }
}
