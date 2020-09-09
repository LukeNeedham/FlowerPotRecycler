package com.lukeneedham.flowerpotrecycler.adapter.delegates.position.implementation

import androidx.recyclerview.widget.*
import com.lukeneedham.flowerpotrecycler.adapter.delegates.position.AdapterPositionDelegate

/** The most basic position delegate - it shows the items in a list in order from first to last */
class LinearPositionDelegate<ItemType>(
    adapter: RecyclerView.Adapter<*>,
    diffCallback: DiffUtil.ItemCallback<ItemType>
) : AdapterPositionDelegate<ItemType> {

    private val asyncListDiffer = AsyncListDiffer<ItemType>(
        AdapterListUpdateCallback(adapter),
        AsyncDifferConfig.Builder(diffCallback).build()
    )

    override fun submitList(list: List<ItemType>, onDiffDoneCallback: () -> Unit) {
        asyncListDiffer.submitList(list, onDiffDoneCallback)
    }

    override fun getItems(): List<ItemType> = asyncListDiffer.currentList

    override fun getItemAt(position: Int): ItemType = asyncListDiffer.currentList[position]

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun getPositionOfItem(item: ItemType) = asyncListDiffer.currentList.indexOf(item)
}
