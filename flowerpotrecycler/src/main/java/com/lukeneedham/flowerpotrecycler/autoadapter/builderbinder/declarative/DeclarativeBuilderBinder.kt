package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.BuilderBinder

/**
 * An implementation of [BuilderBinder] which combines the building of the View, and the binding of the item,
 * into a single override. Useful if you are building the layout programmatically (for example, with Anko DSL Layout).
 *
 * Call [onItem][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative.DeclarativeBindingDsl.onItem]
 * within your implementation of [build] to add a callback to bind the item to the View.
 */
abstract class DeclarativeBuilderBinder<ItemType> : BuilderBinder<ItemType>() {
    private val bindingManager = DeclarativeBindingManager<ItemType>()

    abstract val builder: DeclarativeBindingDsl<ItemType>.(ViewGroup) -> View

    final override fun build(parent: ViewGroup): View {
        bindingManager.currentViewId = parent.hashCode()
        val dsl = DeclarativeBindingDsl(bindingManager)
        return builder(dsl, parent)
    }

    final override fun bind(position: Int, item: ItemType, itemView: View) {
        bindingManager.viewIdToBindCallbacks.forEach { (viewHash, callbacks) ->
            if (viewHash == itemView.hashCode()) {
                callbacks.forEach {
                    it(item)
                }
            }
        }
    }
}
