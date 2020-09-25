package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

/** Use this delegate to add layout params to your item views */
class ItemLayoutParamsDelegate<ItemType, ItemViewType : View>(
    private val layoutParams: RecyclerView.LayoutParams
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {

    override fun onViewHolderCreated(
        parent: ViewGroup,
        viewType: Int,
        viewHolder: ViewHolder<ItemViewType>,
        itemView: ItemViewType
    ) {
        itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}
