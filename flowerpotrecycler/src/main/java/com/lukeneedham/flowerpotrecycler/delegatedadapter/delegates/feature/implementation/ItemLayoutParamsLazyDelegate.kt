package com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.implementation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.delegatedadapter.delegates.feature.BaseAdapterFeatureDelegate

/**
 * The same as [ItemLayoutParamsDelegate], except the constructor parameter is a creator function
 * to allow layout params to be calculated lazily.
 * Useful to delay measuring until the initial layout passes are complete.
 */
class ItemLayoutParamsLazyDelegate<ItemType>(
    private val layoutParamsCreator: () -> RecyclerView.LayoutParams
) : BaseAdapterFeatureDelegate<ItemType>() {

    private val layoutParams by lazy { layoutParamsCreator() }

    override fun onViewHolderCreated(
        viewHolder: RecyclerView.ViewHolder,
        parent: ViewGroup,
        viewType: Int
    ) {
        viewHolder.itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}
