package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.RecyclerItemView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.TypedRecyclerViewHolder
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate

/** Use this delegate to add layout params to your item views */
class LayoutParamsDelegate<ItemType, ItemViewType>(
    private val layoutParams: RecyclerView.LayoutParams
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>()
        where ItemViewType : View, ItemViewType : RecyclerItemView<ItemType> {

    override fun onViewHolderCreated(
        viewHolder: TypedRecyclerViewHolder<ItemType, ItemViewType>,
        parent: ViewGroup,
        viewType: Int
    ) {
        viewHolder.itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}