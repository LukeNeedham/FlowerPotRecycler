package com.lukeneedham.flowerpotrecycler.simpleadapter

interface SimpleRecyclerItemView<ItemType> {
    fun setItem(position: Int, item: ItemType)
}