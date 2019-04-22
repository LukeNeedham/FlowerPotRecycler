package com.lukeneedham.flowerpotrecycler.simpleadapter

import android.view.View

interface SimpleRecyclerItemView<ItemType> {
    fun setItem(item: ItemType, itemView: View)
}