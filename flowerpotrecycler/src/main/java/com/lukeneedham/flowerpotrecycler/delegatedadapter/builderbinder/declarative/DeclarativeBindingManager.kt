package com.lukeneedham.flowerpotrecycler.delegatedadapter.builderbinder.declarative

import com.lukeneedham.flowerpotrecycler.FlowerPotRecyclerException

class DeclarativeBindingManager<ItemType> {
    val viewIdToBindCallbacks = mutableMapOf<Int, MutableList<(ItemType) -> Unit>>()
    var currentViewId: Int? = null

    fun requireCurrentViewId(): Int =
        currentViewId ?: throw FlowerPotRecyclerException("currentViewId must be set")
}
