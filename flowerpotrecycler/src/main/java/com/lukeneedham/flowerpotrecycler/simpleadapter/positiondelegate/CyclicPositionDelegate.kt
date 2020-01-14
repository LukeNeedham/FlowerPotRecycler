package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class CyclicPositionDelegate<ItemType>(
    adapter: RecyclerView.Adapter<*>,
    diffCallback: DiffUtil.ItemCallback<ItemType>
) :
    AdapterPositionDelegate<ItemType> {
        
    private val asyncListDiffer = AsyncListDiffer<ItemType>(
        AdapterListUpdateCallback(adapter),
        AsyncDifferConfig.Builder<ItemType>(diffCallback).build()
    )
        
    override fun submitList(list: List<ItemType>) {
        asyncListDiffer.submitList(list)
    }
        
    override fun getItems(): List<ItemType> = asyncListDiffer.currentList

    override fun getItemAt(position: Int): ItemType {
        val items = asyncListDiffer.currentList
        
        val positionInCycle = position % items.size
        return items[positionInCycle]
    }

    override fun getItemCount() = asyncListDiffer.currentList.size * ITEM_COUNT
        
    override fun getPositionOfItem(item: ItemType): Int {
        val items = asyncListDiffer.currentList
        
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
