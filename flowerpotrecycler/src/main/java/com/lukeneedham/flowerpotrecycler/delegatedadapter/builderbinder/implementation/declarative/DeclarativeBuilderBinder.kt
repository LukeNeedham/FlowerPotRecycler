package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.declarative

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.BuilderBinder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ClassMatcher
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.matcher.ItemMatcher

/**
 * An implementation of [BuilderBinder] which combines the building of the View, and the binding of the item,
 * into a single override. Useful if you are building the layout programmatically (for example, with Anko DSL Layout).
 *
 * Call [onItem][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative.DeclarativeBindingDsl.onItem]
 * within your implementation of [build] to add a callback to bind the item to the View.
 */
class DeclarativeBuilderBinder<ItemType : Any>(
    override val itemMatcher: ItemMatcher<ItemType>,
    private val builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
) : BuilderBinder<ItemType, View>() {

    private val bindingManager = DeclarativeBindingManager<ItemType>()

    override fun build(parent: ViewGroup): View {
        bindingManager.currentViewId = parent.hashCode()
        val dsl = DeclarativeBindingDsl(bindingManager)
        return builder(dsl, parent)
    }

    override fun bind(itemView: View, position: Int, item: ItemType) {
        bindingManager.viewIdToBindCallbacks.forEach { (viewHash, callbacks) ->
            if (viewHash == itemView.hashCode()) {
                callbacks.forEach {
                    it(item)
                }
            }
        }
    }

    companion object {
        inline fun <reified ItemType : Any> fromType(
            noinline builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
        ): DeclarativeBuilderBinder<ItemType> =
            DeclarativeBuilderBinder(
                ClassMatcher(
                    ItemType::class
                ), builder)
    }
}
