package com.lukeneedham.flowerpotrecycler.adapter.itemtype.builderbinder.implementation.declarative

class DeclarativeBindingDsl<ItemType>(private val binder: DeclarativeBinder<ItemType>) {

    /**
     * Use within [DeclarativeBuilderBinder.build]
     * to add a callback to update the View when an item is bound.
     * @param callback The action to perform when an item is bound to this View
     */
    fun onItem(callback: (ItemType) -> Unit) {
        binder.addBindCallback(callback)
    }
}
