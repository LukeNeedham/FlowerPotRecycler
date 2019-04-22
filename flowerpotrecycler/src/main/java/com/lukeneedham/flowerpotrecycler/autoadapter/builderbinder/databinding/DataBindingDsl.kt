package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.databinding

class DataBindingDsl<ItemType>(private val dataBindingManager: DataBindingManager<ItemType>) {

    /**
     * Use within [build][com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.BuilderBinder.build]
     * to add a callback to update the View when an item is bound.
     * @param callback The action to perform when an item is bound to this View
     */
    fun onItem(callback: (ItemType) -> Unit) {
        val currentBuilderParentViewId = dataBindingManager.currentViewId!!
        val existingCallbacks =
            dataBindingManager.viewIdToBindCallbacks[currentBuilderParentViewId] ?: mutableListOf()
        existingCallbacks.add(callback)
        dataBindingManager.viewIdToBindCallbacks[currentBuilderParentViewId] = existingCallbacks
    }
}