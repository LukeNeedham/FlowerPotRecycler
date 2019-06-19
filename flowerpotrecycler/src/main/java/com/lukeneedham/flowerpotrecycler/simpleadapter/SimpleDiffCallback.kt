package com.lukeneedham.flowerpotrecycler.simpleadapter

import androidx.recyclerview.widget.DiffUtil

class SimpleDiffCallback<ItemType>(private val oldList: List<ItemType>, private val newList: List<ItemType>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}