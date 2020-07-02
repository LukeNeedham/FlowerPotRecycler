package com.lukeneedham.flowerpotrecycler.delegatedadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TypedRecyclerViewHolder<ItemType, ItemViewType>(val typedItemView: ItemViewType) :
    RecyclerView.ViewHolder(typedItemView)
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType>