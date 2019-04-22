package com.lukeneedham.flowerpotrecycler.autoadapter.builderbinder.databinding

class DataBindingManager<ItemType> {
    val viewIdToBindCallbacks = mutableMapOf<Int, MutableList<(ItemType) -> Unit>>()
    var currentViewId: Int? = null
}