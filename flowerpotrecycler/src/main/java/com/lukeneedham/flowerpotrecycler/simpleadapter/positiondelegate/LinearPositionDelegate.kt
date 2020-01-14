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
        
    override fun submitList(list: List<ItemType>) {
        asyncListDiffer.submitList(list)
    }
        
    override fun getItems(): List<ItemType> = asyncListDiffer.currentList

    override fun getItemAt(position: Int) = asyncListDiffer.currentList[position]

    override fun getItemCount() = asyncListDiffer.currentList.size
        
    override fun getPositionOfItem(item: ItemType) = asyncListDiffer.currentList.indexOf(item)
}
