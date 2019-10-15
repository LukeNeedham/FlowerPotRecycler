package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.declarative

class DeclarativeBindingManager<ItemType> {
    val viewIdToBindCallbacks = mutableMapOf<Int, MutableList<(ItemType) -> Unit>>()
    var currentViewId: Int? = null
}