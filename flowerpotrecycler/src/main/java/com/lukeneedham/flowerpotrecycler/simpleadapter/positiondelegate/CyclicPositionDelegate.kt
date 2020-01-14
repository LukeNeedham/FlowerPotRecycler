package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class CyclicPositionDelegate<ItemType>(private var items: List<ItemType> = emptyList()) :
    AdapterPositionDelegate<ItemType> {
        
    override fun submitList(list: List<ItemType>) {
        this.items = items
    }

    override fun getItemAt(position: Int): ItemType {
        val positionInCycle = position % items.size
        return items[positionInCycle]
    }

    override fun getItemCount() = items.size * ITEM_COUNT
        
    override fun getPositionOfItem(item: ItemType): Int {
        val numberOfCycles = ITEM_COUNT / items.size
        val centerCycleIndex = numberOfCycles / 2
        val startPositionOfCenterCycle = centerCycleIndex * items.size
        
        val positionOfItemInCycle = items.indexOf(item)
        if(positionOfItemInCycle == -1) {
            throw Exception("Item $item does not exist in $items")
        }
        return startPositionOfCenterCycle + positionOfItemInCycle
    }

    companion object {
        // Using Int.MAX_VALUE can cause OOMs. This value should be large enough that the user never reaches the end, but small enough to be memory efficient
        private const val ITEM_COUNT = 10000
    }
}
