package com.lukeneedham.flowerpotrecycler.delegatedadapter.multitype

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class TypedViewHolder<ItemViewType : View>(
    val typedItemView: ItemViewType
) : RecyclerView.ViewHolder(typedItemView)
