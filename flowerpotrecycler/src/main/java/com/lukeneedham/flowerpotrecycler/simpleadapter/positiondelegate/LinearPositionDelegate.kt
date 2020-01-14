package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

class LinearPositionDelegate<ItemType>(private var items: List<ItemType> = emptyList()) :
    AdapterPositionDelegate<ItemType> {
        
    override fun submitList(list: List<ItemType>) {
        this.items = items
    }
        
    override fun getItems(): List<ItemType> = items

    override fun getItemAt(position: Int) = items[position]

    override fun getItemCount() = items.size
        
    override fun getPositionOfItem(item: ItemType) = items.indexOf(item)
}
