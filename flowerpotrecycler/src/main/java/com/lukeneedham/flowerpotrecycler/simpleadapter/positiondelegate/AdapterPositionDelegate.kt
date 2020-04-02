package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

interface AdapterPositionDelegate<ItemType> {
    fun submitList(list: List<ItemType>, onDiffDoneCallback: () -> Unit = {})
    fun getItems(): List<ItemType>
    fun getItemCount(): Int
    fun getItemAt(position: Int): ItemType
    fun getPositionOfItem(item: ItemType): Int
}