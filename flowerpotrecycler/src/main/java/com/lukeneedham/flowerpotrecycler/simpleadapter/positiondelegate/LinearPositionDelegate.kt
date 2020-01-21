package com.lukeneedham.flowerpotrecycler.simpleadapter.positiondelegate

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class LinearPositionDelegate<ItemType>(
    adapter: RecyclerView.Adapter<*>,
    diffCallback: DiffUtil.ItemCallback<ItemType>
) :
    AdapterPositionDelegate<ItemType> {

    private val asyncListDiffer = AsyncListDiffer<ItemType>(
        AdapterListUpdateCallback(adapter),
        AsyncDifferConfig.Builder<ItemType>(diffCallback).build()
    )

    override fun submitList(list: List<ItemType>, onDiffDoneCallback: () -> Unit) {
        asyncListDiffer.submitList(list, onDiffDoneCallback)
    }

    override fun getItems(): List<ItemType> = asyncListDiffer.currentList

    override fun getItemAt(position: Int) = asyncListDiffer.currentList[position]

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun getPositionOfItem(item: ItemType) = asyncListDiffer.currentList.indexOf(item)
}