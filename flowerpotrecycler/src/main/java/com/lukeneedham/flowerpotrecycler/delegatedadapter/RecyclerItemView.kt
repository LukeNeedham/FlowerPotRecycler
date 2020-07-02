package com.lukeneedham.flowerpotrecycler.delegatedadapter

interface RecyclerItemView<ItemType> {
    fun setItem(position: Int, item: ItemType)
}