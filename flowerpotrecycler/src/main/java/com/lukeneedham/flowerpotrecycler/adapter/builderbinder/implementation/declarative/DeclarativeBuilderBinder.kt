package com.lukeneedham.flowerpotrecycler.adapter.builderbinder.implementation.declarative

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.adapter.builderbinder.BuilderBinder

/**
 * An implementation of [BuilderBinder] which combines the building of the View, and the binding of the item,
 * into a single override. Useful if you are building the layout programmatically (for example, with Anko DSL Layout).
 *
 * Call [DeclarativeBindingDsl.onItem]
 * within your implementation of [build] to add a callback to bind the item to the View.
 */
class DeclarativeBuilderBinder<ItemType : Any>(
    private val builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
) : BuilderBinder<ItemType, View> {

    private val binder = DeclarativeBinder<ItemType>()

    override fun build(parent: ViewGroup): View {
        binder.onPreViewBuild()
        val dsl = DeclarativeBindingDsl(binder)
        val view = builder(dsl, parent)
        binder.onPostViewBuild(view)
        return view
    }

    override fun bind(itemView: View, position: Int, item: ItemType) {
        binder.bind(itemView, item)
    }

    companion object {
        inline fun <reified ItemType : Any> create(
            noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
        ): DeclarativeBuilderBinder<ItemType> =
            DeclarativeBuilderBinder(builder)
    }
}
