package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.declarative

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.ItemBuilderBinder
import kotlin.reflect.KClass

/**
 * An implementation of [ItemBuilderBinder] which combines the building of the View, and the binding of the item,
 * into a single override. Useful if you are building the layout programmatically (for example, with Anko DSL Layout).
 *
 * Call [onItem][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative.DeclarativeBindingDsl.onItem]
 * within your implementation of [build] to add a callback to bind the item to the View.
 */
class DeclarativeBuilderBinder<ItemType : Any>(
    override val itemTypeClass: KClass<ItemType>,
    private val builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View
) : ItemBuilderBinder<ItemType, View>() {
    private val bindingManager = DeclarativeBindingManager<ItemType>()

    override fun createView(parent: ViewGroup): View {
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
            DeclarativeBuilderBinder(ItemType::class, builder)
    }
}
