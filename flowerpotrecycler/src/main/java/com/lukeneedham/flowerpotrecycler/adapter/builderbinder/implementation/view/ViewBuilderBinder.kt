package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.view

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Binder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.Builder
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createReflectiveBuilder
import com.lukeneedham.flowerpotrecycler.util.BuilderBinderUtils.createEmptyBinder

/** builds the view with [builder] and binds with [binder] */
@Suppress("unused")
class ViewBuilderBinder<ItemType : Any, ItemViewType : View>(
    private val builder: Builder<ItemViewType>,
    private val binder: Binder<ItemType, ItemViewType>
) : BuilderBinder<ItemType, ItemViewType> {

    override fun build(parent: ViewGroup): ItemViewType {
        return builder(parent)
    }

    override fun bind(itemView: ItemViewType, position: Int, item: ItemType) {
        binder(itemView, position, item)
    }

    companion object {
        inline fun <reified ItemType : Any, reified ItemViewType : View> newInstance(
            noinline builder: Builder<ItemViewType> = createReflectiveBuilder(),
            noinline binder: Binder<ItemType, ItemViewType> = createEmptyBinder()
        ): ViewBuilderBinder<ItemType, ItemViewType> = ViewBuilderBinder(builder, binder)
    }
}
