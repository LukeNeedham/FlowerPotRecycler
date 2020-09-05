package com.lukeneedham.flowerpotrecycler.staticview.delegates.implementation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukeneedham.flowerpotrecycler.staticview.StaticViewRecyclerViewHolder
import com.lukeneedham.flowerpotrecycler.staticview.delegates.BaseStaticViewAdapterFeatureDelegate

/**
 * The same as [StaticViewLayoutParamsDelegate], except the constructor parameter is a creator function
 * to allow layout params to be calculated lazily.
 * Useful to delay measuring until the initial layout passes are complete.
 */
class StaticViewLayoutParamsLazyDelegate(
    private val layoutParamsCreator: () -> RecyclerView.LayoutParams
) : BaseStaticViewAdapterFeatureDelegate() {

    private val layoutParams by lazy { layoutParamsCreator() }

    override fun onViewHolderCreated(
        viewHolder: StaticViewRecyclerViewHolder,
        parent: ViewGroup,
        viewType: Int
    ) {
        viewHolder.itemView.layoutParams = RecyclerView.LayoutParams(layoutParams)
    }
}
