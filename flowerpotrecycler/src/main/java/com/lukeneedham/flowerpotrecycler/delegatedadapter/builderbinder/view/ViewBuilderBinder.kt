package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Binder
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.build
import kotlin.reflect.KClass

/** builds the view with [builder] and binds with [binder] */
class ViewBuilderBinder<ItemType : Any, ItemViewType : View>(
    override val itemMatcher: ItemMatcher<ItemType>,
    private val builder: Builder<ItemViewType>,
    private val binder: Binder<ItemType, ItemViewType>
) : BuilderBinder<ItemType, ItemViewType>() {

    override fun build(parent: ViewGroup): ItemViewType {
        return builder(parent)
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }

    companion object {
        fun <ItemType : Any, ItemViewType : View> fromItemClass(
            itemClass: KClass<ItemType>,
            builder: Builder<ItemViewType>,
            binder: Binder<ItemType, ItemViewType>
        ): ViewBuilderBinder<ItemType, ItemViewType> =
            ViewBuilderBinder(ClassMatcher(itemClass), builder, binder)

        fun <ItemType : Any, ItemViewType : View> fromItemClassAutoBuild(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>,
            binder: Binder<ItemType, ItemViewType>
        ): ViewBuilderBinder<ItemType, ItemViewType> =
            fromItemClass(
                itemClass,
                builder = { viewClass.build(it.context) },
                binder = binder
            )

        inline fun <reified ItemType : Any, reified ItemViewType : View> fromItemType(
            noinline builder: Builder<ItemViewType>,
            noinline binder: Binder<ItemType, ItemViewType>
        ): ViewBuilderBinder<ItemType, ItemViewType> =
            fromItemClass(ItemType::class, builder, binder)

        inline fun <reified ItemType : Any, reified ItemViewType : View> fromItemTypeAutoBuild(
            noinline binder: Binder<ItemType, ItemViewType>
        ): ViewBuilderBinder<ItemType, ItemViewType> =
            fromItemClassAutoBuild(ItemType::class, ItemViewType::class, binder)
    }
}
