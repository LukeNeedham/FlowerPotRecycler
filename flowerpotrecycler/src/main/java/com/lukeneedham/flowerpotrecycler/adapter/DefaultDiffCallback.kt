package com.lukeneedham.flowerpotrecycler.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * A basic [DiffUtil.ItemCallback] which checks for both item and content equality based on [Any.equals]
 */
class DefaultDiffCallback<ItemType> : DiffUtil.ItemCallback<ItemType>() {
    override fun areItemsTheSame(oldItem: ItemType, newItem: ItemType) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ItemType, newItem: ItemType) =
        oldItem == newItem
}
