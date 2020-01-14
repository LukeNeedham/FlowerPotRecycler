package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class LinearPositionDelegate<ItemType>(
    adapter: RecyclerView.Adapter<*>,
    diffCallback: DiffUtil.ItemCallback<ItemType>
):
    AdapterPositionDelegate<ItemType> {
        
    private val asyncListDiffer = AsyncListDiffer<ItemType>(
        AdapterListUpdateCallback(adapter),
        AsyncDifferConfig.Builder<ItemType>(diffCallback).build()
    )
        
    private val items: List<ItemType>
        get() = asyncListDiffer.currentList
        
    override fun submitList(list: List<ItemType>) {
        asyncListDiffer.submitList(list)
    }
        
    override fun getItems(): List<ItemType> = items

    override fun getItemAt(position: Int) = items[position]

    override fun getItemCount() = items.size
        
    override fun getPositionOfItem(item: ItemType) = items.indexOf(item)
}
