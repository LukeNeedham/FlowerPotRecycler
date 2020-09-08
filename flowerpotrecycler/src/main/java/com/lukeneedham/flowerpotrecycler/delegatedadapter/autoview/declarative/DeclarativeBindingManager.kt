package com.lukeneedham.flowerpotrecycler.delegatedadapter.autoview.declarative

class DeclarativeBindingManager<ItemType> {
    val viewIdToBindCallbacks = mutableMapOf<Int, MutableList<(ItemType) -> Unit>>()
    var currentViewId: Int? = null
}
