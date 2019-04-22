package com.lukeneedham.flowerpotrecycler.simpleadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimpleRecyclerViewHolder<ItemType, ItemViewType>(val typedItemView: ItemViewType) :
    RecyclerView.ViewHolder(typedItemView)
        where ItemViewType : View, ItemViewType : SimpleRecyclerItemView<ItemType>