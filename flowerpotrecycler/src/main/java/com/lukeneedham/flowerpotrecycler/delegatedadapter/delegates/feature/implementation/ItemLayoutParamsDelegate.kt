package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate

/** Use this delegate to add layout params to your item views */
class ItemLayoutParamsDelegate<ItemType>(
    private val layoutParams: RecyclerView.LayoutParams
) : BaseAdapterFeatureDelegate<ItemType>() {

    override fun onViewHolderCreated(
        viewHolder: RecyclerView.ViewHolder,
        parent: ViewGroup,
        viewType: Int
    ) {
        viewHolder.itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}
