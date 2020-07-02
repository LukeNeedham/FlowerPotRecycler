package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.position

/**
 * A delegate for the adapter to use.
 * It is responsible for all the information related to items and their positions.
 */
interface AdapterPositionDelegate<ItemType> {
    fun submitList(list: List<ItemType>, onDiffDoneCallback: () -> Unit = {})
    fun getItems(): List<ItemType>
    fun getItemCount(): Int
    fun getItemAt(position: Int): ItemType
    fun getPositionOfItem(item: ItemType): Int
}