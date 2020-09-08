package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.Binder
import com.lukeneedham.flowerpotrecycler.Builder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder
import com.lukeneedham.flowerpotrecycler.util.extensions.createBuilder
import kotlin.reflect.KClass

/** builds the view with [builder] and binds with [binder] */
@Suppress("unused")
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
            ViewBuilderBinder(
                ClassMatcher(
                    itemClass
                ), builder, binder
            )

        fun <ItemType : Any, ItemViewType : View> fromItemClassAutoBuild(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>,
            binder: Binder<ItemType, ItemViewType>
        ): ViewBuilderBinder<ItemType, ItemViewType> =
            fromItemClass(
                itemClass,
                builder = viewClass.createBuilder(),
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

        fun <ItemType : Any, ItemViewType : View> fromStaticClass(
            itemClass: KClass<ItemType>,
            viewClass: KClass<ItemViewType>
        ): ViewBuilderBinder<ItemType, ItemViewType> {
            return fromItemClassAutoBuild(itemClass, viewClass, createEmptyBinder())
        }

        inline fun <reified ItemType : Any, reified ItemViewType : View> fromStaticType():
                ViewBuilderBinder<ItemType, ItemViewType> =
            fromStaticClass(ItemType::class, ItemViewType::class)
    }
}
