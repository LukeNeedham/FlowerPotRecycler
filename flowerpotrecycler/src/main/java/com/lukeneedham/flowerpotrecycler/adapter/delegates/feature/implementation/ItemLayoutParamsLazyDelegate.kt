package com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.implementation

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.adapter.ViewHolder
import com.lukeneedham.flowerpotrecycler.adapter.delegates.feature.BaseAdapterFeatureDelegate

/**
 * The same as [ItemLayoutParamsDelegate], except the constructor parameter is a creator function
 * to allow layout params to be calculated lazily.
 * Useful to delay measuring until the initial layout passes are complete.
 */
class ItemLayoutParamsLazyDelegate<ItemType, ItemViewType : View>(
    private val layoutParamsCreator: () -> RecyclerView.LayoutParams
) : BaseAdapterFeatureDelegate<ItemType, ItemViewType>() {

    private val layoutParams by lazy { layoutParamsCreator() }

    override fun onViewHolderCreated(
        parent: ViewGroup,
        viewType: Int,
        viewHolder: ViewHolder<ItemViewType>,
        itemView: ItemViewType
    ) {
        itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}
