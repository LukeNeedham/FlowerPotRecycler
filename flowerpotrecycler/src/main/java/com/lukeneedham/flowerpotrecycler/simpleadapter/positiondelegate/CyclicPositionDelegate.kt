package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class CyclicPositionDelegate<ItemType>(private val items: List<ItemType>) :
    AdapterPositionDelegate<ItemType> {

    override fun getItemAt(position: Int): ItemType {
        val cyclicPosition = position % ITEM_COUNT
        return items[cyclicPosition]
    }

    override fun getItemCount() =
        ITEM_COUNT

    companion object {
        // Using Int.MAX_VALUE can cause OOMs. This value should be large enough that the user never reaches the end, but small enough to be memory efficient
        const val ITEM_COUNT = 10000
    }
}