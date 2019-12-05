package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class CyclicPositionDelegate<ItemType>(private val items: List<ItemType>) :
    AdapterPositionDelegate<ItemType> {

    override fun getItemAt(position: Int): ItemType {
        val positionInCycle = position % items.size
        return items[positionInCycle]
    }

    override fun getItemCount() = items.size * NUMBER_OF_CYCLES
        
    override fun getPositionOfItem(item: ItemType): Int {
        val centerCycleIndex = NUMBER_OF_CYCLES / 2
        val startPositionOfCenterCycle = centerCycleIndex * items.size
        
        val positionOfItemInCycle = items.indexOf(item)
        if(positionOfItemInCycle == -1) {
            throw Exception("Item $item does not exist in $items")
        }
        return startPositionOfCenterCycle + positionOfItemInCycle
    }

    companion object {
        // Using Int.MAX_VALUE can cause OOMs. This value should be large enough that the user never reaches the end, but small enough to be memory efficient
        const val NUMBER_OF_CYCLES = 1000
    }
}
