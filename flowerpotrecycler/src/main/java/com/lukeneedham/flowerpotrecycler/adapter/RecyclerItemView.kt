package com.lukeneedham.flowerpotrecycler.adapter

interface RecyclerItemView<ItemType> {
    fun setItem(position: Int, item: ItemType)
}
