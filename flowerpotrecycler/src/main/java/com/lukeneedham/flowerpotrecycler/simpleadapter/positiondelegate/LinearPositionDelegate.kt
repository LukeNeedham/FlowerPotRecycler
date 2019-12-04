package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class LinearPositionDelegate<ItemType>(private val items: List<ItemType>) :
    AdapterPositionDelegate<ItemType> {

    override fun getItemAt(position: Int) = items[position]

    override fun getItemCount() = items.size
}