package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Binder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.matcher.ItemMatcher
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createBuilder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

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
        inline fun <reified ItemType : Any, reified ItemViewType : View> create(
            matcher: ItemMatcher<ItemType> = ClassMatcher(ItemType::class),
            noinline builder: Builder<ItemViewType> = createBuilder(),
            noinline binder: Binder<ItemType, ItemViewType> = createEmptyBinder()
        ): ViewBuilderBinder<ItemType, ItemViewType> =
            ViewBuilderBinder(matcher, builder, binder)
    }
}
