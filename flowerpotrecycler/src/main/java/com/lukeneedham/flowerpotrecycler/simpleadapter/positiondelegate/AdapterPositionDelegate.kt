package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

interface AdapterPositionDelegate<ItemType> {
    fun getItemCount(): Int
    fun getItemAt(position: Int): ItemType
    fun getPositionOfItem(item: ItemType): Int
}
