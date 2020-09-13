package com.lukeneedham.flowerpotrecycler.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ViewHolder<ViewType : View>(val typedItemView: ViewType) :
    RecyclerView.ViewHolder(typedItemView)
