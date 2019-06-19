package com.lukeneedham.flowerpotrecycler.simpleadapter

import android.view.View

interface SimpleRecyclerItemView<ItemType> {
    fun setItem(position: Int, item: ItemType, itemView: View)
}