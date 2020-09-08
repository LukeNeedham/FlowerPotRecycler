package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.implementation.declarative

class DeclarativeBindingDsl<ItemType>(private val bindingManager: DeclarativeBindingManager<ItemType>) {

    /**
     * Use within [DeclarativeBuilderBinder.build]
     * to add a callback to update the View when an item is bound.
     * @param callback The action to perform when an item is bound to this View
     */
    fun onItem(callback: (ItemType) -> Unit) {
        val currentBuilderParentViewId = bindingManager.requireCurrentViewId()
        val existingCallbacks =
            bindingManager.viewIdToBindCallbacks[currentBuilderParentViewId] ?: mutableListOf()
        existingCallbacks.add(callback)
        bindingManager.viewIdToBindCallbacks[currentBuilderParentViewId] = existingCallbacks
    }
}
