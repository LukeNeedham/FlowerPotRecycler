package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.databinding

import android.view.View
import android.view.ViewGroup
import com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.BuilderBinder

/**
 * An implementation of [BuilderBinder] which combines the building of the View, and the binding of the item,
 * into a single override. Useful if you are building the layout programmatically (for example, with Anko DSL Layout).
 *
 * Call [onItem][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.databinding.DataBindingDsl.onItem]
 * within your implementation of [build] to add a callback to bind the item to the View.
 */
abstract class DataBindingBuilderBinder<ItemType> : BuilderBinder<ItemType>() {
    private val bindingManager = DataBindingManager<ItemType>()

    abstract val builder: DataBindingDsl<ItemType>.(ViewGroup) -> View

    final override fun build(parent: ViewGroup): View {
        bindingManager.currentViewId = parent.hashCode()
        val dsl = DataBindingDsl(bindingManager)
        return builder(dsl, parent)
    }

    final override fun bind(item: ItemType, itemView: View) {
        bindingManager.viewIdToBindCallbacks.forEach { (viewHash, callbacks) ->
            if (viewHash == itemView.hashCode()) {
                callbacks.forEach {
                    it(item)
                }
            }
        }
    }
}