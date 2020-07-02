package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DefaultDiffCallback<ItemType> : DiffUtil.ItemCallback<ItemType>() {
    override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType) =
        oldItem == newItem
}